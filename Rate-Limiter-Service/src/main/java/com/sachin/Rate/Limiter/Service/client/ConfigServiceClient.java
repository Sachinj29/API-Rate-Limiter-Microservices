package com.sachin.Rate.Limiter.Service.client;

import com.sachin.Rate.Limiter.Service.dto.RateLimitRule;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "configuration-service")
public interface ConfigServiceClient {
    @GetMapping("/rules/{userId}")
    RateLimitRule getRule(@PathVariable("userId") String userId);
}
