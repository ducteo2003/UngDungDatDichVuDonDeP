package com.example.happyhomes.Model;

import java.io.Serializable;
import java.util.Date;

public class Schedule {
    private Long scheduleId;
    private Long cusId;
    private Date date;
    private Date startTime;
    private String location;
    private String status;

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Long getCusId() {
        return cusId;
    }

    public void setCusId(Long cusId) {
        this.cusId = cusId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Schedule(Long scheduleId, Long cusId, Date date, Date startTime, String location, String status) {
        this.scheduleId = scheduleId;
        this.cusId = cusId;
        this.date = date;
        this.startTime = startTime;
        this.location = location;
        this.status = status;
    }
}
