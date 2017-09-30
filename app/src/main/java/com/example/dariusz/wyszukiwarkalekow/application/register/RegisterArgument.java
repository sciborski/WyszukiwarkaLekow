package com.example.dariusz.wyszukiwarkalekow.application.register;



public class RegisterArgument {

    private String username;
    private String password;
    private String email;
    private int enabled;

    public RegisterArgument(String username,String password,String email){
        this.username = username;
        this.password = password;
        this.email = email;
        this.enabled = 1;
    }

    public String getNickname() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public int getEnabled() {
        return enabled;
    }
}
