package com.example.dariusz.wyszukiwarkalekow;

import java.io.Serializable;

public class Products implements Serializable{
    String name;
    String type;
    double price;
    String town;
    String street;
    String id;
    int idProduct;

    public String getName(){
        return name;
    }
    public String getType(){
        return type;
    }
    public double getPrice(){
        return price;
    }
    public String getTown(){
        return town;
    }
    public String getStreet(){
        return street;
    }
    public String getId(){
        return id;
    }
    public int getIdProduct(){
        return idProduct;
    }
}
