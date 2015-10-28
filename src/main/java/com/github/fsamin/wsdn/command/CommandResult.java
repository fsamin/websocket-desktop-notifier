package com.github.fsamin.wsdn.command;

/**
 * Created by fsamin on 05/10/15.
 */
public enum CommandResult {
    SUCCESS, FAILURE;

    public static CommandResult get(String s) {
        if ("SUCCESS".equals(s)) {
            return SUCCESS;
        }
        return FAILURE;
    }
}
