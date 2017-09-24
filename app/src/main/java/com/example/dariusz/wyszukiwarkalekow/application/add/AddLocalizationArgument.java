package com.example.dariusz.wyszukiwarkalekow.application.add;


public class AddLocalizationArgument {
    String town;
    String street;
    String price;
    String id_user;
    String id_product;

    public AddLocalizationArgument(String town, String street, String price, String id_user, String id_product){
        this.town = town;
        this.street = street;
        this.price = price;
        this.id_user = id_user;
        this.id_product = id_product;
    }

    public String getId_product() {
        return id_product;
    }

    public String getId_user() {
        return id_user;
    }

    public String getStreet() {
        return street;
    }

    public String getTown() {
        return town;
    }

    public String getPrice() {
        return price;
    }


}
