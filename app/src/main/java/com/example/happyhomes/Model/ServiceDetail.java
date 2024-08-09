package com.example.happyhomes.Model;

public class ServiceDetail {
    private int cusId;
    private int serviceId;
    private int payId;
    private String timeAndArea;
    private String addServices;

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

    public String getTimeAndArea() {
        return timeAndArea;
    }

    public void setTimeAndArea(String timeAndArea) {
        this.timeAndArea = timeAndArea;
    }

    public String getAddServices() {
        return addServices;
    }

    public void setAddServices(String addServices) {
        this.addServices = addServices;
    }

    public ServiceDetail(int cusId, int serviceId, int payId, String timeAndArea, String addServices) {
        this.cusId = cusId;
        this.serviceId = serviceId;
        this.payId = payId;
        this.timeAndArea = timeAndArea;
        this.addServices = addServices;
    }
}
