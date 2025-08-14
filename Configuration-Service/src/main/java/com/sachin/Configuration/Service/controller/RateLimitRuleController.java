package com.sachin.Configuration.Service.controller;



import com.sachin.Configuration.Service.model.RateLimitRule;
import com.sachin.Configuration.Service.service.RateLimitRuleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rules")
public class RateLimitRuleController {

    private final RateLimitRuleService ruleService;

    public RateLimitRuleController(RateLimitRuleService ruleService) {
        this.ruleService = ruleService;
    }

    @PostMapping
    public ResponseEntity<RateLimitRule> createRule(@RequestBody RateLimitRule rule) {
        RateLimitRule savedRule = ruleService.saveRule(rule);
        return ResponseEntity.ok(savedRule);
    }

    @GetMapping
    public ResponseEntity<List<RateLimitRule>> getAllRules() {
        return ResponseEntity.ok(ruleService.getAllRules());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<RateLimitRule> getRuleByUserId(@PathVariable String userId) {
        return ruleService.getRuleByUserId(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{userId}")
    public ResponseEntity<RateLimitRule> updateRule(@PathVariable String userId, @RequestBody RateLimitRule updatedRule) {
        return ruleService.getRuleByUserId(userId)
                .map(existingRule -> {
                    existingRule.setLimit(updatedRule.getLimit());
                    existingRule.setWindowInSeconds(updatedRule.getWindowInSeconds());
                    RateLimitRule saved = ruleService.saveRule(existingRule);
                    return ResponseEntity.ok(saved);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteRule(@PathVariable String userId) {
        ruleService.deleteRuleByUserId(userId);
        return ResponseEntity.noContent().build();
    }
}
