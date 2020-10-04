package com.example.foodie.Models;

public class User {
    private String userID, name, phone, email, userType;

    public User() {
        this.userID = "null";
        this.name = "null";
        this.phone = "null";
        this.email = "null";
        this.userType = "null";
    }

    public User(String userID, String name, String phone, String email, String userType) {
        this.userID = userID;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.userType = userType;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
