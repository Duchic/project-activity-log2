package com.example.projectactivitylog.controllers;

import com.example.projectactivitylog.dto.PersonDto;
import com.example.projectactivitylog.dto.ProjectDto;
import com.example.projectactivitylog.entities.ProjectEntity;
import com.example.projectactivitylog.servicess.ProjectService;
import com.example.projectactivitylog.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProjectController {

    private List<ProjectEntity> projectsDB = List.of(new ProjectEntity(1, "test", "test", Constants.STATUS_ACTIVE),
            new ProjectEntity(2, "test2", "Test2",Constants.STATUS_DONE),
            new ProjectEntity(3, "test3", "test3", Constants.STATUS_STOPPED));
    private final ProjectService projectService;
    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("/project/create") //hotovo
    public ProjectDto createNewProject(@RequestBody ProjectDto projectDto) {
        return projectService.createNewProject(projectDto);
    }
    @CrossOrigin
    @GetMapping("/project/get/{id}") //hotovo
    public ProjectDto getProject(@PathVariable int id) {
        return projectService.getProjectById(id);
    }
    @CrossOrigin
    @GetMapping("/project") //testovaci vrati list
    public List<ProjectEntity> getAllProjects() {
        return projectsDB;
    }

    @PostMapping("/project/update/{id}") //hotovo
    public ProjectDto updateProject(@PathVariable int id, @RequestBody ProjectDto projectDto) {
        return projectService.updateProject(projectDto);
    }

    @DeleteMapping("/project/delete/{id}") //hotovo
    public void deleteProject(@PathVariable int id) {
        projectService.deleteProject(id);
    }

    //@GetMapping("/")
    //public String hello() {
       // return "Hello";
    //}


}
