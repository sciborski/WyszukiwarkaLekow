package com.example.dariusz.wyszukiwarkalekow.application.search;


public class SearchMedicinesArgument {

    private String city;

    private String name;

    public SearchMedicinesArgument(String city, String name) {
        this.city = city;
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public String getName() {
        return name;
    }
}
