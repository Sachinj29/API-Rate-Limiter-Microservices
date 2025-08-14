package com.sachin.Rate.Limiter.Service.dto;


public class CheckLimitResponse {
    private boolean allowed;

    public CheckLimitResponse() {}

    public CheckLimitResponse(boolean allowed) {
        this.allowed = allowed;
    }

    public boolean isAllowed() { return allowed; }
    public void setAllowed(boolean allowed) { this.allowed = allowed; }
}
