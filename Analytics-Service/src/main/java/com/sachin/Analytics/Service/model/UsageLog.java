package com.sachin.Analytics.Service.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "usage_logs")
public class UsageLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;
    private boolean allowed;
    private LocalDateTime timestamp;

    public UsageLog() {}

    public UsageLog(String userId, boolean allowed, LocalDateTime timestamp) {
        this.userId = userId;
        this.allowed = allowed;
        this.timestamp = timestamp;
    }

    public Long getId() { return id; }
    public String getUserId() { return userId; }
    public boolean isAllowed() { return allowed; }
    public LocalDateTime getTimestamp() { return timestamp; }

    public void setId(Long id) { this.id = id; }
    public void setUserId(String userId) { this.userId = userId; }
    public void setAllowed(boolean allowed) { this.allowed = allowed; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
