package com.example.happyhomes.Model;

public class Payment {
    private int payId;
    private int methodId;
    private int cusId;
    private int serviceId;
    private String payDay;
    private double payMoney;

    public int getPayId() {
        return payId;
    }

    public void setPayId(int payId) {
        this.payId = payId;
    }

    public int getMethodId() {
        return methodId;
    }

    public void setMethodId(int methodId) {
        this.methodId = methodId;
    }

    public int getCusId() {
        return cusId;
    }

    public void setCusId(int cusId) {
        this.cusId = cusId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getPayDay() {
        return payDay;
    }

    public void setPayDay(String payDay) {
        this.payDay = payDay;
    }

    public double getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(double payMoney) {
        this.payMoney = payMoney;
    }

    public Payment(int payId, int methodId, int cusId, int serviceId, String payDay, double payMoney) {
        this.payId = payId;
        this.methodId = methodId;
        this.cusId = cusId;
        this.serviceId = serviceId;
        this.payDay = payDay;
        this.payMoney = payMoney;
    }
}
