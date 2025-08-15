package com.sachin.Configuration.Service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "rate_limit_rules")
public class RateLimitRule {

    @Id
    @Column(name = "user_id", nullable = false)
    private String userId;

    // “limit” is a reserved keyword in PostgreSQL, so map it to a safe column name.
    @Column(name = "req_limit", nullable = false)
    private int limit;

    @Column(name = "window_in_seconds", nullable = false)
    private int windowInSeconds;

    public RateLimitRule() { }

    public RateLimitRule(String userId, int limit, int windowInSeconds) {
        this.userId = userId;
        this.limit = limit;
        this.windowInSeconds = windowInSeconds;
    }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public int getLimit() { return limit; }
    public void setLimit(int limit) { this.limit = limit; }

    public int getWindowInSeconds() { return windowInSeconds; }
    public void setWindowInSeconds(int windowInSeconds) { this.windowInSeconds = windowInSeconds; }
}
