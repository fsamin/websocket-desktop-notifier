package com.github.fsamin.wsdn;

import com.github.fsamin.wsdn.ui.login.LoginManager;
import com.github.fsamin.wsdn.ui.TrayIcon;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

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

        Scene scene = new Scene(new StackPane());

        LoginManager loginManager = new LoginManager(scene);
        loginManager.showLoginScreen();

        primaryStage.setScene(scene);
        primaryStage.show();

        TrayIcon.createTrayIcon(primaryStage);
        Platform.setImplicitExit(false);
    }

}
