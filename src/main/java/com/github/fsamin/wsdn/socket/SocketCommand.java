package com.github.fsamin.wsdn.socket;

import com.github.fsamin.wsdn.command.CommandResult;
import com.github.fsamin.wsdn.command.CommandType;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import org.eclipse.jetty.websocket.api.Session;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
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

    public JsonObject getData() {
        return data;
    }

    private CommandResult result;
    private JsonObject data;

    public SocketCommand(){}

    public SocketCommand(CommandType type, JsonObject data) {
        this.type = type;
        this.data = data;
    }

    public SocketCommand(CommandType type, Object data) {
        this.type = type;
        this.data = new Gson().toJsonTree(data).getAsJsonObject();
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
        JsonParser parser = new JsonParser();
        InputStream is = new ByteArrayInputStream(json.getBytes());
        InputStreamReader isr = new InputStreamReader(is);
        JsonReader reader = new JsonReader(isr);
        reader.setLenient(true);
        JsonObject object = parser.parse(reader).getAsJsonObject();

        SocketCommand cmd = new SocketCommand();

        try {
            cmd.type = CommandType.get(object.get("type").getAsString());
            cmd.data = object.getAsJsonObject("data");
            cmd.result = CommandResult.get(object.get("result").getAsString());

            return cmd;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
