package com.example.dariusz.wyszukiwarkalekow.data.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "oauth")
public class OAuthEntity {

    @DatabaseField(id = true)
    private String name;

    @DatabaseField(canBeNull = false)
    private String value;

    public OAuthEntity() { }

    public OAuthEntity(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
