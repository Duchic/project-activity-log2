package com.example.projectactivitylog.servicess;

import com.example.projectactivitylog.dto.PersonDto;
import com.example.projectactivitylog.entities.PersonEntity;
import com.example.projectactivitylog.repositories.PersonRepository;
import com.vaadin.flow.router.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<PersonDto> getAllPerson() {
//        List<PersonEntity> allPerson = personRepository.findAll();
//        List<PersonDto> dtoList = new ArrayList<>();
//
//        for (int i=0; i<allPerson.size(); i++) {
//            PersonDto personDto = new PersonDto();
//            personDto.setId(allPerson.get(i).getId());
//            personDto.setName(allPerson.get(i).getName());
//            personDto.setSurname(allPerson.get(i).getSurname());
//            dtoList.add(i, personDto);
//        }
//        return dtoList;
        return personRepository.findAll().stream()
                .map(PersonDto::of)
                .collect(Collectors.toList());
    }

    public PersonDto getPersonById(int id) {
//        Optional<PersonEntity> byId = personRepository.findById(id);
//        PersonDto personDto = new PersonDto();
//        if (byId.isPresent()) {
//            personDto.setId(byId.get().getId());
//            personDto.setName(byId.get().getName());
//            personDto.setSurname(byId.get().getSurname());
//            return personDto;
//        }
//        return null;

        return PersonDto.of(personRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException())
                );

    }

    public PersonDto createNewPerson(PersonDto personDto) {
        PersonEntity personEntity = new PersonEntity();
        personEntity.setName(personDto.getName());
        personEntity.setSurname(personDto.getSurname());
        personRepository.save(personEntity);
        personDto.setId(personEntity.getId());
        return personDto;
    }

    public PersonDto updatePerson(PersonDto personDto) {
        PersonEntity personEntity = new PersonEntity();
        personEntity.setId(personDto.getId());
        personEntity.setName(personDto.getName());
        personEntity.setSurname(personDto.getSurname());
        personRepository.save(personEntity);
        return personDto; //musim nasetovat id?
    }

    public void deletePerson(int id) {
        personRepository.deleteById(id);
    }
}
