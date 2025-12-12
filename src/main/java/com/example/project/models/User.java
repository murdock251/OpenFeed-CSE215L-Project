package com.example.project.models;

public class User {
    private String userId;
    private String username;
    private String name;
    private String password;
    private String email;
    private String phoneNumber;
    private String address;

    public User(String username, String name, String password, String email, String phoneNumber, String address) {
        this.userId = generateId();
        this.username = username;
        this.name = name;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber != null ? phoneNumber : "";
        this.address = address != null ? address : "";
    }


    public User(String userId, String username, String name, String password, String email, String phoneNumber, String address) {
        this.userId = userId;
        this.username = username;
        this.name = name;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }



    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        if (password != null && password.length() >= 6) {
            this.password = password;
        }
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    private String generateId() {
        return "U" + System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return "User: " + name + " (@" + username + ")";
    }
}