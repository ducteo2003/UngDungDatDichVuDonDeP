package com.example.happyhomes.Model;

public class ServiceSchedule {
    private int serviceId;
    private int scheduleId;

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public ServiceSchedule(int serviceId, int scheduleId) {
        this.serviceId = serviceId;
        this.scheduleId = scheduleId;
    }
}
