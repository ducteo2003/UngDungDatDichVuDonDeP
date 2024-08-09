package com.example.happyhomes.Model;


public class Customer {
    private int cusId;
    private String cusName;
    private String phoneNumber;
    private String email;
    private String address;
    private String username;
    private String password;

    public int getCusId() {
        return cusId;
    }

    public void setCusId(int cusId) {
        this.cusId = cusId;
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Customer(int cusId, String cusName, String phoneNumber, String email, String address, String username, String password) {
        this.cusId = cusId;
        this.cusName = cusName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.username = username;
        this.password = password;
    }
}
