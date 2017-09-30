package com.example.dariusz.wyszukiwarkalekow.data.dto;

import com.example.dariusz.wyszukiwarkalekow.application.base.UseCase;

import java.io.Serializable;



public class Localizations implements Serializable{
    String town;
    String street;
    double price;
    int id_location;
    Products id_product;
    Users id_user;


    public double getPrice(){
        return price;
    }
    public String getTown(){
        return town;
    }
    public String getStreet(){
        return street;
    }
    public int getIdLocation(){ return id_location; }
    public Products getProducts(){ return id_product; }
    public Users getUsers(){ return id_user; }
}
