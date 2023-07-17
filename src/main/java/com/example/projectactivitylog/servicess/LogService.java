package com.example.projectactivitylog.servicess;

import com.example.projectactivitylog.dto.LogDto;
import com.example.projectactivitylog.entities.LogEntity;
import com.example.projectactivitylog.entities.ProjectEntity;
import com.example.projectactivitylog.repositories.LogRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LogService {

    private final LogRepository logRepository;

    public LogService(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public List<LogDto> getAllRecord() {
        List<LogEntity> allRecord = logRepository.findAll();
        List<LogDto> dtoList = new ArrayList<>();

        for (int i=0; i<allRecord.size(); i++) {
            LogDto dto = new LogDto();
            dto.setId(allRecord.get(i).getId());
            dto.setStart(allRecord.get(i).getStart());
            dto.setStop(allRecord.get(i).getStop());
            dto.setProject_id(allRecord.get(i).getProject_id());
            dto.setPerson_id(allRecord.get(i).getPerson_id());
            dtoList.add(i, dto);
        }
        return dtoList;
    }

    public LogDto getLogById(int id) {
        Optional<LogEntity> byId = logRepository.findById(id);
        LogDto logDto = new LogDto();
        if (byId.isPresent()) {
            logDto.setId(byId.get().getId());
            logDto.setStart(byId.get().getStart());
            logDto.setStop(byId.get().getStop());
            logDto.setProject_id(byId.get().getProject_id());
            logDto.setPerson_id(byId.get().getPerson_id());
            return logDto;
        }
        return logDto;
    }

    public LogDto createNewLog(LogDto logDto) {
        LogEntity logEntity = new LogEntity();
        logEntity.setStart(logDto.getStart());
        logEntity.setStop(logDto.getStop());
        logEntity.setProject_id(logDto.getProject_id());
        logEntity.setPerson_id(logDto.getPerson_id());
        logRepository.save(logEntity);
        logDto.setId(logEntity.getId());
        return logDto;
    }

    public LogDto updateLog(LogDto logDto) {
        LogEntity logEntity = new LogEntity();
        logEntity.setId(logDto.getId());
        logEntity.setStart(logDto.getStart());
        logEntity.setStop(logDto.getStop());
        logEntity.setProject_id(logDto.getProject_id());
        logEntity.setPerson_id(logDto.getPerson_id());
        logRepository.save(logEntity);
        return logDto;
    }

    public void deleteLog(int id) {
        logRepository.deleteById(id);
    }
}
