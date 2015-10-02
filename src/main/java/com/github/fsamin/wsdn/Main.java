package com.github.fsamin.wsdn;

import com.github.fsamin.wsdn.handler.SocketHandler;
import com.github.fsamin.wsdn.ui.Notifier;
import com.github.fsamin.wsdn.ui.TrayIcon;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;

import java.net.URI;
import java.util.concurrent.TimeUnit;

/**
 * Created by fsamin on 01/10/15.
 */
public class Main extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.setProperty("apple.awt.UIElement", "true");

        TrayIcon.createTrayIcon(primaryStage);
        Platform.setImplicitExit(false);

        String destUri = "ws://echo.websocket.org";
        WebSocketClient client = new WebSocketClient();
        SocketHandler socket = new SocketHandler();
        Notifier notifier = Notifier.INSTANCE;
        try {
            client.start();
            URI echoUri = new URI(destUri);
            ClientUpgradeRequest request = new ClientUpgradeRequest();
            client.connect(socket, echoUri, request);
            System.out.printf("Connecting to : %s%n", echoUri);
            notifier.notifySuccess("Connection successfull", "connected to " + echoUri);
            socket.awaitClose(60, TimeUnit.SECONDS);
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            try {
                client.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
