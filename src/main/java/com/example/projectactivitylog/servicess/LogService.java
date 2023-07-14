package com.example.projectactivitylog.servicess;

import com.example.projectactivitylog.repositories.LogRepository;
import org.springframework.stereotype.Service;

@Service
public class LogService {

    private final LogRepository logRepository;

    public LogService(LogRepository logRepository) {
        this.logRepository = logRepository;
    }
}
