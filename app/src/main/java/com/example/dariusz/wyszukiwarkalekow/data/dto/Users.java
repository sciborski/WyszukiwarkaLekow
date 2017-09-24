package com.example.dariusz.wyszukiwarkalekow.data.dto;

import java.io.Serializable;



public class Users implements Serializable {
    String username;
    int id_user;
    String data;

    public String getUsername(){ return username; }
    public int getIdUser(){ return id_user; }
    public String getData(){ return data; }
}
