package com.example.happyhomes.Model;

public class Check_Work {
    private Long checkId;
    private Long workdateId;
    private byte[] checkPic;
    private Integer checkType;
    private String time;

    public Long getWorkdateId() {
        return workdateId;
    }

    public void setWorkdateId(Long workdateId) {
        this.workdateId = workdateId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getCheckType() {
        return checkType;
    }

    public void setCheckType(Integer checkType) {
        this.checkType = checkType;
    }

    public byte[] getCheckPic() {
        return checkPic;
    }

    public void setCheckPic(byte[] checkPic) {
        this.checkPic = checkPic;
    }

    public Long getCheckId() {
        return checkId;
    }

    public void setCheckId(Long checkId) {
        this.checkId = checkId;
    }

    public Check_Work(Long checkId, Long workdateId, byte[] checkPic, Integer checkType, String time) {
        this.checkId = checkId;
        this.workdateId = workdateId;
        this.checkPic = checkPic;
        this.checkType = checkType;
        this.time = time;
    }
}