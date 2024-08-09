package com.example.happyhomes.Model;

public class PayMethod {
    private int methodId;
    private String method;

    public int getMethodId() {
        return methodId;
    }

    public void setMethodId(int methodId) {
        this.methodId = methodId;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public PayMethod(int methodId, String method) {
        this.methodId = methodId;
        this.method = method;
    }
}
