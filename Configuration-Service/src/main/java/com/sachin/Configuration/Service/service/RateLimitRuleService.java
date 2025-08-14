package com.sachin.Configuration.Service.service;



import com.sachin.Configuration.Service.model.RateLimitRule;
import com.sachin.Configuration.Service.repository.RateLimitRuleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RateLimitRuleService {

    private final RateLimitRuleRepository repository;

    public RateLimitRuleService(RateLimitRuleRepository repository) {
        this.repository = repository;
    }

    public RateLimitRule saveRule(RateLimitRule rule) {
        return repository.save(rule);
    }

    public List<RateLimitRule> getAllRules() {
        return repository.findAll();
    }

    public Optional<RateLimitRule> getRuleByUserId(String userId) {
        return repository.findById(userId);
    }

    public void deleteRuleByUserId(String userId) {
        repository.deleteById(userId);
    }
}
