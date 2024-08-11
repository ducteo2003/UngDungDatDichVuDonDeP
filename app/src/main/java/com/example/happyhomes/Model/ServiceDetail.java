package com.example.happyhomes.Model;

public class ServiceDetail {
    private int cusId;
    private int serviceId;
    private int payId;
    private String note;

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

    public int getPayId() {
        return payId;
    }

    public void setPayId(int payId) {
        this.payId = payId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public ServiceDetail(int cusId, int serviceId, int payId, String note) {
        this.cusId = cusId;
        this.serviceId = serviceId;
        this.payId = payId;
        this.note = note;
    }
}
