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

    // Map<userId, UserRequestData>
    private final Map<String, UserRequestData> requestStore = new ConcurrentHashMap<>();

    public RateLimiterCoreService(ConfigServiceClient configClient) {
        this.configClient = configClient;
    }

    public synchronized boolean isRequestAllowed(String userId) {
        RateLimitRule rule = configClient.getRule(userId);

        // If no rule exists, allow all
        if (rule == null) {
            return true;
        }

        long now = Instant.now().getEpochSecond();
        UserRequestData data = requestStore.getOrDefault(userId, new UserRequestData(0, now));

        // If window expired - reset counter
        if (now - data.windowStart >= rule.getWindowInSeconds()) {
            data.requestCount = 0;
            data.windowStart = now;
        }

        // Increment and check
        data.requestCount++;
        requestStore.put(userId, data);

        return data.requestCount <= rule.getLimit();
    }

    // Inner class to store user request count and window start time
    private static class UserRequestData {
        int requestCount;
        long windowStart;

        UserRequestData(int requestCount, long windowStart) {
            this.requestCount = requestCount;
            this.windowStart = windowStart;
        }
    }
}
