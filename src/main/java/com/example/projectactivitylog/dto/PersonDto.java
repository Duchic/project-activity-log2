package com.example.projectactivitylog.dto;

import com.example.projectactivitylog.entities.PersonEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class PersonDto {

    private Integer id;

    private String name;

    private String surname;

    public static PersonDto of(PersonEntity personEntity) {
        return PersonDto.builder()
                .id(personEntity.getId())
                .name(personEntity.getName())
                .surname(personEntity.getSurname())
                .build();
    }
}
