package com.wgmc.whattobuy.pojo;

/**
 * Created by notxie on 27.01.17.
 */

public class Shop {
    private long id;
    private String name;
    private String address;
    private Shoptype type;

    public Shop(long id) {
        this.id = id;
        this.name = "";
        this.address = "";
        this.type = Shoptype.OTHER;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Shoptype getType() {
        return type;
    }

    public void setType(Shoptype type) {
        this.type = type;
    }
}
