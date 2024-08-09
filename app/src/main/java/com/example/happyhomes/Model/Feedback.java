package com.example.happyhomes.Model;

public class Feedback {
    private int feedbackId;
    private int payId;
    private String content;

    public int getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(int feedbackId) {
        this.feedbackId = feedbackId;
    }

    public int getPayId() {
        return payId;
    }

    public void setPayId(int payId) {
        this.payId = payId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Feedback(int feedbackId, int payId, String content) {
        this.feedbackId = feedbackId;
        this.payId = payId;
        this.content = content;
    }
}
