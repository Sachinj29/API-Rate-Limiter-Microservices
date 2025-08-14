package com.sachin.Rate.Limiter.Service.dto;


public class CheckLimitRequest {
    private String userId;

    public CheckLimitRequest() {}

    public CheckLimitRequest(String userId) {
        this.userId = userId;
    }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
}
