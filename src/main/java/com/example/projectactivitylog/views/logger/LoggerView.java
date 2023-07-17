package com.example.projectactivitylog.views.logger;

import com.example.projectactivitylog.dto.LogDto;
import com.example.projectactivitylog.dto.PersonDto;
import com.example.projectactivitylog.dto.ProjectDto;
import com.example.projectactivitylog.servicess.LogService;
import com.example.projectactivitylog.servicess.PersonService;
import com.example.projectactivitylog.servicess.ProjectService;
import com.example.projectactivitylog.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.selection.SelectionEvent;
import com.vaadin.flow.data.selection.SelectionListener;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.Command;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

//import static jdk.jfr.internal.consumer.EventLog.start;

@PageTitle("Logger")
@Route(value = "log", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class LoggerView extends Div {

    private Timer timer;
    private LocalDateTime start;
    private LocalDateTime stop;

    private Button btnStart = new Button("Start");
    private Button btnStop = new Button("Stop");
    private ComboBox<ProjectDto> projectDtoComboBox = new ComboBox<>("Project");
    private ComboBox<PersonDto> personDtoComboBox = new ComboBox<>("Person");

    private Div div = new Div();
    private Binder<LogDto> binder = new Binder<>(LogDto.class);
    private ProjectService projectService;
    private LogService logService;

    private PersonService personService;
    private AtomicInteger seconds = new AtomicInteger(0);
    private Grid<LogDto> grid;


    public LoggerView(LogService logService, ProjectService projectService, PersonService personService) {
        this.logService = logService;
        this.personService = personService;
        personDtoComboBox.setItemLabelGenerator(personDto -> personDto.getName() + " " + personDto.getSurname());
        this.projectService = projectService;
        projectDtoComboBox.setItemLabelGenerator(ProjectDto::getName);
        addClassName("logger-view");
        configureBinder();
        grid = new Grid<>(LogDto.class, false);
        grid.addColumn(LogDto::getId).setHeader("ID");
        grid.addColumn(logDto -> logDto.getStart().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"))).setHeader("Start");
        grid.addColumn(LogDto::getStop).setHeader("Stop");
        grid.addColumn(LogDto::getProject_id).setHeader("Project");
        grid.addColumn(LogDto::getPerson_id).setHeader("Person");
        grid.setItems(logService.getAllRecord());

        grid.addSelectionListener(new SelectionListener<Grid<LogDto>, LogDto>() {
            @Override
            public void selectionChange(SelectionEvent<Grid<LogDto>, LogDto> selectionEvent) {
                Optional<LogDto> firstSelectionItem = selectionEvent.getFirstSelectedItem();
                if (firstSelectionItem.isPresent()) {
                    binder.setBean(firstSelectionItem.get());
                }
            }
        });

        add(createTitle());
        add(grid);
        add(createFormLayout());
        add(createButtonLayout());
        setComboBoxValues();
        div.addClassName("timer");
        add(div);
        btnStart.setEnabled(false);
        btnStop.setEnabled(false);
        personDtoComboBox.addValueChangeListener(e -> enableButtonStart());
        projectDtoComboBox.addValueChangeListener(e -> enableButtonStart());
        btnStart.addClickListener(buttonClickEvent -> startStopWatch());
        btnStop.addClickListener(buttonClickEvent -> stopStopWatch());

    }

    private void enableButtonStart() {
        if (personDtoComboBox.getValue() != null && projectDtoComboBox.getValue() != null){
            btnStart.setEnabled(true);
        }
    }


    private void startStopWatch() {
        btnStop.setEnabled(true);
        start = LocalDateTime.now();
        timer = new Timer();
        UI current = UI.getCurrent();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                seconds.incrementAndGet();
                current.access(new Command() {
                    @Override
                    public void execute() {
                        int second = seconds.get();
                        int hours = calculateHours(second);
                        int min = calculateMin(second);
                        int sec = calculateSec(second);
                        div.setText(hours + ":" + min + ":" + sec);
                    }
                });
            }
        };
        timer.scheduleAtFixedRate(task, 1000, 1000);
        System.out.println("start " + start);
    }

    private int calculateHours(int seconds) {
        int hour = seconds/60/60;
        return hour;
    }

    private int calculateMin(int seconds) {
        int min = (seconds%3600)/60;
        return min;
    }

    private int calculateSec(int seconds) {
        int sec = seconds%60;
        return sec;
    }

    private void stopStopWatch() {
        if (timer != null) {
            timer.cancel();
            timer = null;
            stop = LocalDateTime.now();

        }
        System.out.println("stop " + stop);
        seconds.set(0);
        LogDto logDto = new LogDto();
        logDto.setStart(start);
        logDto.setStop(stop);
        logDto.setProject_id(projectDtoComboBox.getValue().getId());
        logDto.setPerson_id(personDtoComboBox.getValue().getId());
        logService.createNewLog(logDto);
        grid.setItems(logService.getAllRecord());
    }

    private void setComboBoxValues() {
        List<ProjectDto> list = projectService.getAllProject();
        projectDtoComboBox.setItems(list);
        List<PersonDto> listPerson = personService.getAllPerson();
        personDtoComboBox.setItems(listPerson);
    }

    private void configureBinder() {
        //binder.bind(btnStart, LogDto::getStart, (LogDto::setStart));
    }

    private Component createTitle() {
        return new H3("Logger");
    }

    private void clearForm() {
        binder.setBean(new LogDto());
    }

    private Component createFormLayout() {
        FormLayout formLayout = new FormLayout();
        formLayout.add(projectDtoComboBox, personDtoComboBox);
        return formLayout;
    }

    private Component createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        btnStart.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(btnStart);
        buttonLayout.add(btnStop);
        return buttonLayout;
    }

}
