package com.example.happyhomes.Model;

public class Check {
    private int checkId;
    private int emId;
    private byte[] checkPic;
    private int checkType;

    public int getCheckId() {
        return checkId;
    }

    public void setCheckId(int checkId) {
        this.checkId = checkId;
    }

    public int getEmId() {
        return emId;
    }

    public Check(int checkId, int emId, byte[] checkPic, int checkType) {
        this.checkId = checkId;
        this.emId = emId;
        this.checkPic = checkPic;
        this.checkType = checkType;
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
}
