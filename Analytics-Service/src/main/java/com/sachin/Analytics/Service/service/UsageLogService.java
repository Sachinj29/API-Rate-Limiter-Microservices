package com.sachin.Analytics.Service.service;



import com.sachin.Analytics.Service.model.UsageLog;
import com.sachin.Analytics.Service.repository.UsageLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UsageLogService {

    private final UsageLogRepository repository;

    public UsageLogService(UsageLogRepository repository) {
        this.repository = repository;
    }

    public void logUsage(String userId, boolean allowed) {
        UsageLog log = new UsageLog(userId, allowed, LocalDateTime.now());
        repository.save(log);
    }

    public List<UsageLog> getUserLogs(String userId) {
        return repository.findByUserId(userId);
    }

    public List<UsageLog> getAllLogs() {
        return repository.findAll();
    }
}
