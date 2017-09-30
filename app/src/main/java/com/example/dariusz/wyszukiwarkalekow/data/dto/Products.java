package com.example.dariusz.wyszukiwarkalekow.data.dto;

import java.io.Serializable;

public class Products implements Serializable{
    String name;
    String qr_code;
    int id_product;

    public String getName(){
        return name;
    }
    public String getQrCode(){
        return qr_code;
    }
    public int getIdProduct(){
        return id_product;
    }
}
