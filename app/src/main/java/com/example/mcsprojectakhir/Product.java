package com.example.mcsprojectakhir;

public class Product {

    private String productId;
    private String productName;
    private String productMinPlayer;
    private String productMaxPlayer;
    private int productPrice;
    private String created_at;
    private String longitude;
    private String latitude;

    public Product(String productId, String productName, String productMinPlayer, String productMaxPlayer, int productPrice, String created_at, String longitude, String latitude) {
        this.productId = productId;
        this.productName = productName;
        this.productMinPlayer = productMinPlayer;
        this.productMaxPlayer = productMaxPlayer;
        this.productPrice = productPrice;
        this.created_at = created_at;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductMinPlayer() {
        return productMinPlayer;
    }

    public void setProductMinPlayer(String productMinPlayer) {
        this.productMinPlayer = productMinPlayer;
    }

    public String getProductMaxPlayer() {
        return productMaxPlayer;
    }

    public void setProductMaxPlayer(String productMaxPlayer) {
        this.productMaxPlayer = productMaxPlayer;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}
