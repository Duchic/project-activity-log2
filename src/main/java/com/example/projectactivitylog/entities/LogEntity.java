package com.example.projectactivitylog.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "log")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogEntity {

    public LogEntity(LocalDateTime start, int project, int person){
        this.stop = start;
        this.project_id = project;
        this.person_id = person;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private LocalDateTime start;

    private LocalDateTime stop;

    @Column(nullable = false)
    private int project_id;

    @Column(nullable = false)
    private int person_id;

}
