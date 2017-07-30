package com.example.dariusz.wyszukiwarkalekow.application.login;

/**
 * Created by Seweryn on 30.07.2017.
 */

public class LoginCredentials {

    private String username;
    private String password;

    public LoginCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
