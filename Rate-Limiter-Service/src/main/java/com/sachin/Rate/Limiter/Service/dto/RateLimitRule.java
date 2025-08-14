package com.sachin.Rate.Limiter.Service.dto;


public class RateLimitRule {
    private String userId;
    private int limit;
    private int windowInSeconds;

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public int getLimit() { return limit; }
    public void setLimit(int limit) { this.limit = limit; }

    public int getWindowInSeconds() { return windowInSeconds; }
    public void setWindowInSeconds(int windowInSeconds) { this.windowInSeconds = windowInSeconds; }
}
