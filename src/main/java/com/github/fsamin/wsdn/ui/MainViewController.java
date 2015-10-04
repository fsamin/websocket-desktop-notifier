package com.github.fsamin.wsdn.ui;


import com.github.fsamin.wsdn.ui.login.LoginManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * Controls the main application screen
 */
public class MainViewController {
    @FXML
    private Button logoutButton;
    @FXML
    private Label sessionLabel;

    public void initialize() {
    }

    public void initSessionID(final LoginManager loginManager, String sessionID) {
        sessionLabel.setText(sessionID);
        logoutButton.setOnAction((event) -> loginManager.logout());
    }
}
