package com.example.dariusz.wyszukiwarkalekow.data.dto;

import java.io.Serializable;

public class Products implements Serializable{
    String name;
    String qrCode;
    int idProduct;

    public String getName(){
        return name;
    }
    public String getQrCode(){
        return qrCode;
    }
    public int getIdProduct(){
        return idProduct;
    }
}
