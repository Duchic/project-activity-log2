package com.example.projectactivitylog.repositories;

import com.example.projectactivitylog.entities.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<ProjectEntity, Integer> {
}
