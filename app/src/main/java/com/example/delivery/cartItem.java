package com.example.delivery;

public class cartItem {
    private String itemId;
    private String productName;
    private String productPrice;
    private int quantity;
    private String username;

    public cartItem() {
        // Default constructor required for Firebase
    }

    public cartItem(String itemId, String productName, String productPrice, int quantity, String username) {
        this.itemId = itemId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.quantity = quantity;
        this.username = username;
    }

    // Getters and setters for all fields

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
