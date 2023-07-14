package com.example.projectactivitylog.repositories;

import com.example.projectactivitylog.entities.LogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<LogEntity, Integer> {
}
