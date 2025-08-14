package com.sachin.Analytics.Service.controller;



import com.sachin.Analytics.Service.model.UsageLog;
import com.sachin.Analytics.Service.service.UsageLogService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/analytics")
public class UsageLogController {

    private final UsageLogService logService;

    public UsageLogController(UsageLogService logService) {
        this.logService = logService;
    }

    @GetMapping
    public List<UsageLog> getAllLogs() {
        return logService.getAllLogs();
    }

    @GetMapping("/{userId}")
    public List<UsageLog> getUserLogs(@PathVariable String userId) {
        return logService.getUserLogs(userId);
    }
}
