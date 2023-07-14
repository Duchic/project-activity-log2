package com.example.projectactivitylog.repositories;

import com.example.projectactivitylog.entities.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<PersonEntity, Integer> {
}
