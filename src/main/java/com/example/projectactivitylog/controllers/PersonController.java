package com.example.projectactivitylog.controllers;

import com.example.projectactivitylog.dto.PersonDto;
import com.example.projectactivitylog.entities.PersonEntity;
import com.example.projectactivitylog.servicess.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class PersonController {

    //private List<PersonEntity> personDB = List.of(new PersonEntity(1, "Jan", "Z Rokycan"), //testovaci DB - nutne v getAllPerson dat <ProjectEntity> a vracet tuhle promenou
    //           new PersonEntity(2, "Pavel", "Test"),
    //           new PersonEntity(3, "Dalsi", "Testovaci"));

    private final PersonService personService;


    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping("/person/create") //hotovo
    public PersonDto createNewPerson(@RequestBody PersonDto personDto) {
        return personService.createNewPerson(personDto);
    }

    @GetMapping("/person/get{id}") //hotovo
    public PersonDto getPerson(@PathVariable int id) {
        return personService.getPersonById(id);
    }

    @GetMapping("/person") //testovaci
    public List<PersonDto> getAllPerson() {
        return personService.getAllPerson();
    }

    @PostMapping("/person/update/{id}")
    public PersonDto updatePerson(@PathVariable int id, @RequestBody PersonDto personDto) {
        return personService.updatePerson(personDto);
    }

    @DeleteMapping("/person/delete/{id}")
    public void deletePerson(@PathVariable int id) {
        personService.deletePerson(id);
    }

    //@GetMapping("/")
    //public String hello() {
        //return "Hello";
    //}
}
