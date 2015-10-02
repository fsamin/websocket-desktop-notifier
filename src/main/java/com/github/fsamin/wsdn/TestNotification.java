package com.github.fsamin.wsdn;

/**
 * Created by
 * User: hansolo
 * Date: 01.07.13
 * Time: 07:10
 */

import com.github.fsamin.wsdn.ui.Notifier;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Random;


public class TestNotification extends Application {
    private static final Random RND = new Random();
    private static final Notification[] NOTIFICATIONS = {
            new Notification("Info", "New information", Notification.INFO_ICON),
            new Notification("Warning", "Attention, somethings wrong", Notification.WARNING_ICON),
            new Notification("Success", "Great it works", Notification.SUCCESS_ICON),
            new Notification("Error", "ZOMG", Notification.ERROR_ICON)
    };
    private Notifier notifier;
    private Button button;


    // ******************** Initialization ************************************
    @Override
    public void init() {
        button = new Button("Notify");
        button.setOnAction(event -> {
            notifier.notify(NOTIFICATIONS[RND.nextInt(4)]);
        });
    }


    // ******************** Application start *********************************
    @Override
    public void start(Stage stage) {
        notifier = Notifier.INSTANCE;

        StackPane pane = new StackPane();
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.getChildren().addAll(button);

        Scene scene = new Scene(pane);
        stage.setOnCloseRequest(observable -> notifier.stop());
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() {
    }

    public static void main(String[] args) {
        launch(args);
    }
}
