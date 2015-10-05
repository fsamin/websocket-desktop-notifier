package com.github.fsamin.wsdn.socket;

import com.github.fsamin.wsdn.command.CommandResult;
import com.github.fsamin.wsdn.ui.Notifier;

/**
 * Created by fsamin on 05/10/15.
 */
public class SocketController {
    public static void process(SocketCommand cmd) {
        switch (cmd.getType()) {
            case LOGIN:
                if (CommandResult.SUCCESS.equals(cmd.getResult())) {
                    loginSuccess(cmd);
                } else {
                    loginFailure(cmd);
                }
                break;
            case NOTIFICATION:
                notification(cmd);
                break;
        }
    }

    public static void loginSuccess(SocketCommand cmd) {

    }

    public static void loginFailure(SocketCommand cmd) {
        Notifier.INSTANCE.notifyError("Authentication failure", cmd.getData().toString());
    }

    public static void notification(SocketCommand cmd) {
        Notifier.INSTANCE.notifyInfo("New message", cmd.getData().toString());
    }

}
