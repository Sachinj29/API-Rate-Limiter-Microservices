package com.sachin.Rate.Limiter.Service.controller;


import com.sachin.Rate.Limiter.Service.dto.CheckLimitRequest;
import com.sachin.Rate.Limiter.Service.dto.CheckLimitResponse;
import com.sachin.Rate.Limiter.Service.service.RateLimiterCoreService;
import org.springframework.web.bind.annotation.*;

@RestController
public class RateLimiterController {

    private final RateLimiterCoreService coreService;

    public RateLimiterController(RateLimiterCoreService coreService) {
        this.coreService = coreService;
    }

    @PostMapping("/check-limit")
    public CheckLimitResponse checkLimit(@RequestBody CheckLimitRequest request) {
        boolean allowed = coreService.isRequestAllowed(request.getUserId());
        return new CheckLimitResponse(allowed);
    }
}
