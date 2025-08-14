package com.sachin.Analytics.Service.repository;


import com.sachin.Analytics.Service.model.UsageLog;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UsageLogRepository extends JpaRepository<UsageLog, Long> {
    List<UsageLog> findByUserId(String userId);
}
