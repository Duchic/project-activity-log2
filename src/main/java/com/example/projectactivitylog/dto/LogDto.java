package com.example.projectactivitylog.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LogDto {

    private int id;

    private LocalDateTime start;

    private LocalDateTime stop;

    private int project_id;

    private int person_id;

}
