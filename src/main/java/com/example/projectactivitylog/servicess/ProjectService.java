package com.example.projectactivitylog.servicess;

import com.example.projectactivitylog.dto.ProjectDto;
import com.example.projectactivitylog.entities.ProjectEntity;
import com.example.projectactivitylog.repositories.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }


    public List<ProjectDto> getAllProject() {
        List<ProjectEntity> all = projectRepository.findAll();

        List<ProjectDto> dtoList = new ArrayList<>();

        for (int i=0; i<all.size(); i++) {
            ProjectDto dto = new ProjectDto();
            dto.setId(all.get(i).getId());
            dto.setName(all.get(i).getName());
            dto.setDescription(all.get(i).getDescription());
            dto.setStatus(all.get(i).getStatus());
            dtoList.add(i, dto);
        }
        return dtoList;
    }

    public ProjectDto getProjectById(int id) {
        Optional<ProjectEntity> byId = projectRepository.findById(id);
        ProjectDto projectDto = new ProjectDto();
        if (byId.isPresent()){
            projectDto.setId(byId.get().getId());
            projectDto.setName(byId.get().getName());
            projectDto.setDescription(byId.get().getDescription());
            projectDto.setStatus(byId.get().getStatus());
            return projectDto;
        }
        return null;
    }

    public ProjectDto createNewProject(ProjectDto projectDto) {
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setName(projectDto.getName());
        projectEntity.setDescription(projectDto.getDescription());
        projectEntity.setStatus(projectDto.getStatus());
        projectRepository.save(projectEntity);
        projectDto.setId(projectEntity.getId());
        return projectDto;
    }

    public ProjectDto updateProject(ProjectDto projectDto) {
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setId(projectDto.getId());
        projectEntity.setName(projectDto.getName());
        projectEntity.setDescription(projectDto.getDescription());
        projectEntity.setStatus(projectDto.getStatus());
        projectRepository.save(projectEntity);
        return projectDto; //musim tady nasetovat i id?
    }

    public void deleteProject(int id) {
        projectRepository.deleteById(id);

    }
}
