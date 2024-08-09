package com.example.happyhomes.Model;

public class Employee {
    private int emId;
    private String emName;
    private String emEmail;
    private String password;

    public int getEmId() {
        return emId;
    }

    public void setEmId(int emId) {
        this.emId = emId;
    }

    public String getEmName() {
        return emName;
    }

    public void setEmName(String emName) {
        this.emName = emName;
    }

    public String getEmEmail() {
        return emEmail;
    }

    public void setEmEmail(String emEmail) {
        this.emEmail = emEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Employee(int emId, String emName, String emEmail, String password) {
        this.emId = emId;
        this.emName = emName;
        this.emEmail = emEmail;
        this.password = password;
    }
}
