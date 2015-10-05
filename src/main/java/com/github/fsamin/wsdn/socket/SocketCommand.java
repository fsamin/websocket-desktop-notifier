package com.github.fsamin.wsdn.socket;

import com.github.fsamin.wsdn.command.CommandResult;
import com.github.fsamin.wsdn.command.CommandType;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.eclipse.jetty.websocket.api.Session;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by fsamin on 05/10/15.
 */

public class SocketCommand {

    private CommandType type;

    public CommandType getType() {
        return type;
    }

    public CommandResult getResult() {
        return result;
    }

    public Object getData() {
        return data;
    }

    private CommandResult result;
    private Object data;

    public SocketCommand(CommandType type, Object data) {
        this.type = type;
        this.data = data;
    }

    public String getJSON() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public void send(Session session) throws InterruptedException, ExecutionException, TimeoutException {
        Future<Void> fut;
        fut = session.getRemote().sendStringByFuture(this.getJSON());
        fut.get(1, TimeUnit.MILLISECONDS);
    }

    public void send(Session session, int seconds) throws InterruptedException, ExecutionException, TimeoutException {
        Future<Void> fut;
        fut = session.getRemote().sendStringByFuture(this.getJSON());
        fut.get(seconds, TimeUnit.SECONDS);
    }

    public static SocketCommand fromJSON(String json) {
        Gson gson = new Gson();
        SocketCommand cmd = gson.fromJson(json, SocketCommand.class);
        return cmd;
    }
}
