package com.github.fsamin.wsdn.socket;

import com.github.fsamin.wsdn.command.CommandType;
import com.github.fsamin.wsdn.ui.Notifier;
import com.github.fsamin.wsdn.ui.login.LoginCredential;
import javafx.application.Platform;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;

import java.net.URI;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Created by fsamin on 01/10/15.
 */
@WebSocket(maxTextMessageSize = 64 * 1024)
public class SocketHandler {

    private static SocketHandler instance;
    private String destUri;
    private WebSocketClient client;
    private LoginCredential credential;

    private SocketHandler(String uri) {
        if (uri == null) {
            this.destUri = "ws://echo.websocket.org";
        } else {
            this.destUri = uri;
        }
    }

    public WebSocketClient open(LoginCredential credential) throws Exception {
        this.credential = credential;
        this.client = new WebSocketClient();

        URI echoUri = new URI(destUri);
        ClientUpgradeRequest request = new ClientUpgradeRequest();

        this.client.start();
        this.client.connect(this, echoUri, request);

        Platform.runLater(() ->
                        Notifier.INSTANCE.notifySuccess("Connection successfull", "connected to " + echoUri)
        );
        return client;
    }

    public void close() throws Exception {
        this.client.stop();
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        System.out.printf("Connection closed: %d - %s%n", statusCode, reason);

        Platform.runLater(() ->
                        Notifier.INSTANCE.notifyWarning("Connection closed", statusCode + " | " + reason)
        );
    }

    @OnWebSocketConnect
    public void onConnect(Session session) {
        System.out.printf("Got connect: %s%n", session);
        try {

            SocketCommand cmd = new SocketCommand(CommandType.LOGIN, credential);
            cmd.send(session);

        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    @OnWebSocketMessage
    public void onMessage(String msg) {
        SocketCommand cmd = SocketCommand.fromJSON(msg);
        if (cmd != null) {
            Platform.runLater(() -> SocketController.process(cmd));
        }

        System.out.printf("Got msg: %s%n", msg);
    }

    public static SocketHandler getInstance(String uri) {
        if (instance == null) {
            instance = new SocketHandler(uri);
        }
        return instance;
    }
}
