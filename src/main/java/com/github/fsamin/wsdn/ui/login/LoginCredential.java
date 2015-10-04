package com.github.fsamin.wsdn.ui.login;

import com.google.gson.Gson;

/**
 * Created by fsamin on 04/10/15.
 */
public class LoginCredential {
    private String domain;
    private String username;
    private String password;

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LoginCredential(String domain, String username, String password) {
        this.domain = domain;
        this.username = username;
        this.password = password;
    }

    public String getJSON() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

}
