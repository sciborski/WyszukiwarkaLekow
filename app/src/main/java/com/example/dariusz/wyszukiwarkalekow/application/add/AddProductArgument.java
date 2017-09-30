package com.example.dariusz.wyszukiwarkalekow.application.add;



public class AddProductArgument {
    private String name;
    private String qr_code;

    public AddProductArgument(String name, String qr_code){
        this.name = name;
        this.qr_code = qr_code;
    }

    public String getName() {
        return name;
    }

    public String getQr_code() {
        return qr_code;
    }
}
