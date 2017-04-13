package com.wgmc.whattobuy.pojo;

/**
 * Created by notxie, poidl on 27.01.17. and 10.04.2017.
 */

public class Shop {
    private long id;
    //Declare the variable id with the data typ long
    private String name;
    //Declare the variable name with the data typ String
    private String address;
    //Declare the variable address with the data typ String
    private Shoptype type;
    //Declare the variable type with the data typ Shoptype as a other klass

    public Shop(long id) {
        this.id = id;
        this.name = "";
        this.address = "";
//        this.type = Shoptype.OTHER;
        //This is the constructor for the Class Shop and we initate all the declarations
    }

    //Getter for id
    public long getId() {
        return id;
    }

    //Setter for id
    public void setId(long id) {
        this.id = id;
    }

    //Getter for name
    public String getName() {
        return name;
    }

    //Setter for name
    public void setName(String name) {
        this.name = name;
    }

    //Getter for address
    public String getAddress() {
        return address;
    }

    //Setter for address
    public void setAddress(String address) {
        this.address = address;
    }

    //Getter for type
    public Shoptype getType() {
        return type;
    }

    //Setter for type
    public void setType(Shoptype type) {
        this.type = type;
    }

    //override the to string method with the return value name + id
    @Override
    public String toString() {
        return name + " (" + id + ")";
    }
}
