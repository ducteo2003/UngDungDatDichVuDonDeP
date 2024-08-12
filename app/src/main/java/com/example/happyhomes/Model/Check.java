package com.example.happyhomes.Model;

public class Check {
    private int checkId;
    private int emId;
    private byte[] checkPic;
    private int checkType;
    private String checkTime;

    public int getCheckId() {
        return checkId;
    }

    public void setCheckId(int checkId) {
        this.checkId = checkId;
    }

    public int getEmId() {
        return emId;
    }

    public Check(int emId, byte[] checkPic, int checkType, String checkTime) {
        this.emId = emId;
        this.checkPic = checkPic;
        this.checkType = checkType;
        this.checkTime = checkTime;
    }


    public void setEmId(int emId) {
        this.emId = emId;
    }

    public byte[] getCheckPic() {
        return checkPic;
    }

    public void setCheckPic(byte[] checkPic) {
        this.checkPic = checkPic;
    }

    public int getCheckType() {
        return checkType;
    }

    public void setCheckType(int checkType) {
        this.checkType = checkType;
    }

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }

    //test
    public Check(int checkId, int emId, byte[] checkPic, int checkType, String checkTime) {
        this.checkId = checkId;
        this.emId = emId;
        this.checkPic = checkPic;
        this.checkType = checkType;
        this.checkTime = checkTime;
    }

}
