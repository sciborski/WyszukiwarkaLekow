package com.example.dariusz.wyszukiwarkalekow;

import java.io.Serializable;

/**
 * Created by Dariusz on 2017-02-13.
 */

public class Localizations implements Serializable{
    String town;
    String street;
    double price;
    int idLocation;
    Products idProduct;
    Users idUser;

    public double getPrice(){
        return price;
    }
    public String getTown(){
        return town;
    }
    public String getStreet(){
        return street;
    }
    public int getIdLocation(){ return idLocation; }
    public Products getProducts(){ return idProduct; }
    public Users getUsers(){ return idUser; }
}
