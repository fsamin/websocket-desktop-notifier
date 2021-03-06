package com.github.fsamin.wsdn.ui.login;


import com.github.fsamin.wsdn.socket.SocketHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * Controls the login screen
 */
public class LoginController {

    @FXML
    private TextField domain;
    @FXML
    private TextField user;
    @FXML
    private TextField password;
    @FXML
    private Button loginButton;

    public void initialize() {
    }

    public void initManager(final LoginManager loginManager) {
        loginButton.setOnAction((event) -> {
                    String sessionID = authorize();
                    if (sessionID != null) {
                        loginManager.authenticated(sessionID);
                    }
                }
        );
    }

    /**
     * Check authorization credentials.
     * <p>
     * If accepted, return a sessionID for the authorized session
     * otherwise, return null.
     */
    private String authorize() {
        try {
            SocketHandler.getInstance("ws://localhost:8001").open(new LoginCredential(domain.getText(), user.getText(), password.getText()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return
                "open".equals(user.getText()) && "sesame".equals(password.getText())
                        ? generateSessionID()
                        : null;
    }

    private static int sessionID = 0;

    private String generateSessionID() {
        sessionID++;
        return "xyzzy - session " + sessionID;
    }
}