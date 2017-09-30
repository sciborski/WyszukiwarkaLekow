package com.example.dariusz.wyszukiwarkalekow.data.dto;

import java.io.Serializable;



public class MedicinesResponse implements Serializable {
    String town;
    String street;
    double price;
    int id_user;
    String name;

    public String getTown() {
        return town;
    }

    public String getStreet() {
        return street;
    }

    public double getPrice() {
        return price;
    }

    public int getId_user() {
        return id_user;
    }

    public String getName() {
        return name;
    }
}
