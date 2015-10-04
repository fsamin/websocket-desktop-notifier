package com.github.fsamin.wsdn.ui;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

/**
 * Created by fsamin on 02/10/15.
 */
public class TrayIcon {

    private static java.awt.TrayIcon trayIcon;

    public static void createTrayIcon(final Stage stage) {
        if (SystemTray.isSupported()) {
            // get the SystemTray instance
            SystemTray tray = SystemTray.getSystemTray();
            // load an image
            java.awt.Image image = null;
            try {
                image = ImageIO.read(TrayIcon.class.getResourceAsStream("/info.png"));
            } catch (IOException ex) {
                System.out.println(ex);
            }


            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent t) {
                    TrayIcon.hide(stage);
                }
            });

            // create a popup menu
            PopupMenu popup = new PopupMenu();

            MenuItem showItem = new MenuItem("Show");
            showItem.addActionListener(ae ->
                            Platform.runLater(() -> stage.show())
            );

            popup.add(showItem);

            MenuItem closeItem = new MenuItem("Close");
            closeItem.addActionListener(ae -> System.exit(0));
            popup.add(closeItem);


            // construct a TrayIcon
            trayIcon = new java.awt.TrayIcon(image, "Title", popup);
            // set the TrayIcon properties
            trayIcon.addActionListener(ae ->
                            Platform.runLater(() -> stage.show())
            );
            // add the tray image
            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                System.err.println(e);
            }
        }
    }

    private static void hide(final Stage stage) {
        Platform.runLater(() -> {
            if (SystemTray.isSupported()) {
                stage.hide();
            } else {
                System.exit(0);
            }
        });
    }
}
