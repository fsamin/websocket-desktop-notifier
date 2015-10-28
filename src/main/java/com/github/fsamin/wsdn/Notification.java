package com.github.fsamin.wsdn;

import com.github.fsamin.wsdn.ui.Notifier;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import javafx.scene.image.Image;

public class Notification {
    public static final Image INFO_ICON = new Image(Notifier.class.getResourceAsStream("/info.png"));
    public static final Image WARNING_ICON = new Image(Notifier.class.getResourceAsStream("/warning.png"));
    public static final Image SUCCESS_ICON = new Image(Notifier.class.getResourceAsStream("/success.png"));
    public static final Image ERROR_ICON = new Image(Notifier.class.getResourceAsStream("/error.png"));

    public  String title;
    public  String message;
    public  transient Image image;
    public  String imageURL;

    public Notification(final String TITLE, final String MESSAGE, final Image IMAGE) {
        this.title = TITLE;
        this.message = MESSAGE;
        this.image = IMAGE;
    }

    public Notification(final String TITLE, final String MESSAGE, final String IMAGE) {
        this.title = TITLE;
        this.message = MESSAGE;
        this.image = new Image(IMAGE);
    }

    public static Notification fromJSON(String json) {
        Gson gson = new Gson();
        Notification n = gson.fromJson(json, Notification.class);
        if (n.imageURL != null)
            n.image = new Image(n.imageURL);
        return n;
    }

    public static Notification fromJSON(JsonObject json) {
        Gson gson = new Gson();
        Notification n = gson.fromJson(json, Notification.class);
        if (n.imageURL != null)
            n.image = new Image(n.imageURL);

        return n;
    }

    public String toJSON() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

}