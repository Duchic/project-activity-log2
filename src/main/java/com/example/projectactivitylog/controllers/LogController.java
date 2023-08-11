package com.example.projectactivitylog.controllers;

import com.example.projectactivitylog.dto.LogDto;
import com.example.projectactivitylog.entities.LogEntity;
import com.example.projectactivitylog.servicess.LogService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class LogController {

    private List<LogEntity> logDG = List.of(new LogEntity(1, LocalDateTime.now(), LocalDateTime.now().minusHours(3), 1,1),
            new LogEntity(1, LocalDateTime.now(), LocalDateTime.now().minusHours(1), 2,2),
            new LogEntity(1, LocalDateTime.now(), LocalDateTime.now().minusHours(2), 3,3));


    private final LogService logService;

    @Autowired
    public LogController(LogService logService){
        this.logService = logService;
    }

    @GetMapping("/log")
    public List<LogDto> getAll() {
        return logService.getAllRecord();
    }
}
