package com.github.fsamin.wsdn.handler;

import com.github.fsamin.wsdn.ui.Notifier;
import javafx.application.Platform;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.StatusCode;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Created by fsamin on 01/10/15.
 */
@WebSocket(maxTextMessageSize = 64 * 1024)
public class SocketHandler {

    private final CountDownLatch closeLatch;

    String destUri;

    public SocketHandler(String uri) {
        this.destUri = "ws://echo.websocket.org";
        this.closeLatch = new CountDownLatch(1);

    }

    public WebSocketClient open() throws Exception {
        Notifier notifier = Notifier.INSTANCE;
        WebSocketClient client = new WebSocketClient();
        URI echoUri = new URI(destUri);
        ClientUpgradeRequest request = new ClientUpgradeRequest();
        client.start();
        client.connect(this, echoUri, request);

        Platform.runLater(() ->
                notifier.notifySuccess("Connection successfull", "connected to " + echoUri)
        );
        return client;
    }

    public boolean awaitClose(int duration, TimeUnit unit) throws InterruptedException {
        return this.closeLatch.await(duration, unit);
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        System.out.printf("Connection closed: %d - %s%n", statusCode, reason);
        this.closeLatch.countDown();

        Platform.runLater(() ->
                Notifier.INSTANCE.notifyInfo("Connection closed", statusCode + " | " + reason)
        );

    }

    @OnWebSocketConnect
    public void onConnect(Session session) {
        System.out.printf("Got connect: %s%n", session);
        try {
            Future<Void> fut;
            fut = session.getRemote().sendStringByFuture("Hello");
            fut.get(2, TimeUnit.SECONDS);
            fut = session.getRemote().sendStringByFuture("Thanks for the conversation.");
            fut.get(2, TimeUnit.SECONDS);
            session.close(StatusCode.NORMAL, "I'm done");
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    @OnWebSocketMessage
    public void onMessage(String msg) {
        Platform.runLater(() ->
            Notifier.INSTANCE.notifyInfo("New message", msg)
        );


        System.out.printf("Got msg: %s%n", msg);
    }
}
