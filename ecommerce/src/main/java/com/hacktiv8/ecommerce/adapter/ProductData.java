package com.hacktiv8.ecommerce.adapter;

public class ProductData {

    private String productName;
    private String quantity;
    private int productImage;

    public ProductData(String productName, String quantity, int productImage) {
        this.productName = productName;
        this.quantity = quantity;
        this.productImage = productImage;
    }

    public String getProductName() {
        return productName;
    }

    public String getQuantity() {
        return quantity;
    }

    public int getProductImage() {
        return productImage;
    }
}
