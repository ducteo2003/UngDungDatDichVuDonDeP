package com.example.happyhomes.Model;

public class Workdate {
    private int emId;
    private int scheduleId;

    public Workdate(int emId, int scheduleId) {
        this.emId = emId;
        this.scheduleId = scheduleId;
    }

    public int getEmId() {
        return emId;
    }

    public void setEmId(int emId) {
        this.emId = emId;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }
}
