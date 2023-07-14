package com.example.projectactivitylog.servicess;

import com.example.projectactivitylog.dto.PersonDto;
import com.example.projectactivitylog.entities.PersonEntity;
import com.example.projectactivitylog.repositories.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public PersonDto getPersonById(int id) {
        Optional<PersonEntity> byId = personRepository.findById(id);
        PersonDto personDto = new PersonDto();
        if (byId.isPresent()) {
            personDto.setId(byId.get().getId());
            personDto.setName(byId.get().getName());
            personDto.setSurname(byId.get().getSurname());
            return personDto;
        }
        return null;
    }

    public PersonDto createNewPerson(PersonDto personDto) {
        PersonEntity personEntity = new PersonEntity();
        personEntity.setName(personDto.getName());
        personEntity.setSurname(personDto.getSurname());
        personRepository.save(personEntity);
        personDto.setId(personEntity.getId());
        return personDto;
    }

    public PersonDto updatePerson(int id, PersonDto personDto) {
        PersonEntity personEntity = new PersonEntity();
        personEntity.setName(personDto.getName());
        personEntity.setSurname(personDto.getSurname());
        personRepository.save(personEntity);
        return personDto; //musim nasetovat id?
    }

    public void deletePerson(int id) {
        personRepository.deleteById(id);
    }
}
