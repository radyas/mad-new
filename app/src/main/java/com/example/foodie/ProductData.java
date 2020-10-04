package com.example.foodie;
import com.google.firebase.database.IgnoreExtraProperties;

public class ProductData {
    private String productID;
    private String productName;
    private String price;

    public ProductData(String productID, String productName, String price) {
        this.productID = productID;
        this.productName = productName;
        this.price = price;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
