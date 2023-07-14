package com.example.projectactivitylog.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "project")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;


    private String description;

    @Column(nullable = false)
    private String status;

    public ProjectEntity(String name, String status){
        this.name = name;
        this.status = status;
    }
}
