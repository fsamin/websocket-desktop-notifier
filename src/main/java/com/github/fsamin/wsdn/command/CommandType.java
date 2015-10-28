package com.github.fsamin.wsdn.command;

/**
 * Created by fsamin on 05/10/15.
 */
public enum CommandType {
    LOGIN, NOTIFICATION, UNKNOWN;

    public static CommandType get(String s) {
        if ("LOGIN".equals(s)) {
            return LOGIN;
        }
        if ("NOTIFICATION".equals(s)) {
            return NOTIFICATION;
        }
        return UNKNOWN;
    }
}
