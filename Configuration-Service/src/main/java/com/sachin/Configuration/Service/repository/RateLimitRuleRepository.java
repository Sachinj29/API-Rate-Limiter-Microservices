package com.sachin.Configuration.Service.repository;



import com.sachin.Configuration.Service.model.RateLimitRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RateLimitRuleRepository extends JpaRepository<RateLimitRule, String> {
}
