package com.example.carpartsshop;

public class ShoppingPart {
    private String name;
    private String info;
    private String price;
    private final int imageRes;

    public ShoppingPart(String name, String info, String price, int imageRes) {
        this.name = name;
        this.info = info;
        this.price = price;
        this.imageRes = imageRes;
    }

    public String getName() {
        return name;
    }
    public String getInfo() {
        return info;
    }
    public String getPrice() {
        return price;
    }
    public int getImageRes() {
        return imageRes;
    }

}
