package com.github.fsamin.wsdn;

import com.github.fsamin.wsdn.ui.Notifier;
import javafx.scene.image.Image;

public class Notification {
    public static final Image INFO_ICON = new Image(Notifier.class.getResourceAsStream("/info.png"));
    public static final Image WARNING_ICON = new Image(Notifier.class.getResourceAsStream("/warning.png"));
    public static final Image SUCCESS_ICON = new Image(Notifier.class.getResourceAsStream("/success.png"));
    public static final Image ERROR_ICON = new Image(Notifier.class.getResourceAsStream("/error.png"));
    public final String TITLE;
    public final String MESSAGE;
    public final Image IMAGE;


    // ******************** Constructors **************************************
    public Notification(final String TITLE, final String MESSAGE) {
        this(TITLE, MESSAGE, null);
    }

    public Notification(final String MESSAGE, final Image IMAGE) {
        this("", MESSAGE, IMAGE);
    }

    public Notification(final String TITLE, final String MESSAGE, final Image IMAGE) {
        this.TITLE = TITLE;
        this.MESSAGE = MESSAGE;
        this.IMAGE = IMAGE;
    }


}