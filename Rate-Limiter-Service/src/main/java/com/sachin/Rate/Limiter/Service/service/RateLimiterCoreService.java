package com.sachin.Rate.Limiter.Service.service;

import com.sachin.Rate.Limiter.Service.client.ConfigServiceClient;
import com.sachin.Rate.Limiter.Service.dto.RateLimitRule;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimiterCoreService {

    private final ConfigServiceClient configClient;

    // Map<userId, window data>
    private final Map<String, UserWindow> store = new ConcurrentHashMap<>();

    public RateLimiterCoreService(ConfigServiceClient configClient) {
        this.configClient = configClient;
    }

    public synchronized boolean isRequestAllowed(String userId) {
        RateLimitRule rule;
        try {
            rule = configClient.getRule(userId);
        } catch (Exception e) {
            // If config service is down or user not found, allow by default
            return true;
        }
        if (rule == null) {
            return true;
        }

        long now = Instant.now().getEpochSecond();
        UserWindow w = store.getOrDefault(userId, new UserWindow(0, now));

        // reset window if expired
        if (now - w.windowStart >= rule.getWindowInSeconds()) {
            w.count = 0;
            w.windowStart = now;
        }

        w.count++;
        store.put(userId, w);

        return w.count <= rule.getLimit();
    }

    private static class UserWindow {
        int count;
        long windowStart;
        UserWindow(int count, long windowStart) {
            this.count = count;
            this.windowStart = windowStart;
        }
    }
}
