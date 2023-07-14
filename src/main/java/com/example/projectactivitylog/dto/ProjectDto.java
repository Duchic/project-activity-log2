package com.example.projectactivitylog.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class ProjectDto {


    private int id;


    private String name;


    private String description;


    private String status;


}
