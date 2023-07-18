package com.example.projectactivitylog.views.projects;

import com.example.projectactivitylog.dto.ProjectDto;
import com.example.projectactivitylog.servicess.ProjectService;
import com.example.projectactivitylog.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.selection.SelectionEvent;
import com.vaadin.flow.data.selection.SelectionListener;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.Optional;

@PageTitle("Projects")
@Route(value = "projects", layout = MainLayout.class)
@Uses(Icon.class)
public class ProjectsView extends Div {

    private TextField name = new TextField("Name");
    private TextField description = new TextField("Des");
    private TextField status = new TextField("Stat");

    private Button btnCancel = new Button("Cancel");
    private Button btnSave = new Button("Save");
    private Button btnDelete = new Button("Delete");

    private Button btnNewProject = new Button("New");

    private Binder<ProjectDto> binder = new Binder<>(ProjectDto.class);

    public ProjectsView(ProjectService projectService) {
        addClassName("projects-view");
        configureBinder();
        Grid<ProjectDto> grid = new Grid<>(ProjectDto.class, false);
        grid.addColumn(ProjectDto::getId).setHeader("ID");
        grid.addColumn(ProjectDto::getName).setHeader("Project name");
        grid.addColumn(ProjectDto::getDescription).setHeader("Description");
        grid.addColumn(ProjectDto::getStatus).setHeader("Status");
        grid.setItems(projectService.getAllProject());
        grid.addSelectionListener(new SelectionListener<Grid<ProjectDto>, ProjectDto>() {
            @Override
            public void selectionChange(SelectionEvent<Grid<ProjectDto>, ProjectDto> selectionEvent) {
                Optional<ProjectDto> firstSelectedItem = selectionEvent.getFirstSelectedItem();
                if (firstSelectedItem.isPresent()) {
                    binder.setBean(firstSelectedItem.get());
                }
            }
        });

        add(createTitle());
        add(grid);
        add(createFormLayout());
        add(createButtonLayout());


        //binder.bindInstanceFields(this);
        clearForm();

        btnCancel.addClickListener(e -> clearForm());
        btnSave.addClickListener(e -> {
            ProjectDto projectDto = binder.getBean();
            projectService.updateProject(projectDto);
            grid.setItems(projectService.getAllProject());
            //personService.update(binder.getBean());
            Notification.show("New project stored.");
            clearForm();
        });

        btnNewProject.addClickListener(buttonClickEvent -> {
            ProjectDto projectDto = binder.getBean();
            projectService.createNewProject(projectDto);
            grid.setItems(projectService.getAllProject());
            Notification.show("Details stored.");
            clearForm();
        });

        btnDelete.addClickListener(buttonClickEvent -> {
            ProjectDto projectDto = binder.getBean();
            projectService.deleteProject(projectDto.getId());
            grid.setItems(projectService.getAllProject());
            Notification.show("Project deleted.");
            clearForm();
        });
    }

    private void clearForm() {
        binder.setBean(new ProjectDto());
    }

    private Component createTitle() {
        return new H3("Personal information");
    }

    private Component createFormLayout() {
        FormLayout formLayout = new FormLayout();
        formLayout.add(name, description, status);
        return formLayout;
    }

    private Component createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        btnSave.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(btnSave);
        buttonLayout.add(btnCancel);
        buttonLayout.add(btnNewProject);
        buttonLayout.add(btnDelete);
        return buttonLayout;
    }

    private void configureBinder() {
        binder.bind(name, ProjectDto::getName, (ProjectDto::setName));
        binder.bind(description, ProjectDto::getDescription, (ProjectDto::setDescription));
        binder.bind(status, ProjectDto::getStatus, (ProjectDto::setStatus));
    }

    private static class PhoneNumberField extends CustomField<String> {
        private ComboBox<String> countryCode = new ComboBox<>();
        private TextField number = new TextField();

        public PhoneNumberField(String label) {
            setLabel(label);
            countryCode.setWidth("120px");
            countryCode.setPlaceholder("Country");
            countryCode.setAllowedCharPattern("[\\+\\d]");
            countryCode.setItems("+354", "+91", "+62", "+98", "+964", "+353", "+44", "+972", "+39", "+225");
            countryCode.addCustomValueSetListener(e -> countryCode.setValue(e.getDetail()));
            number.setAllowedCharPattern("\\d");
            HorizontalLayout layout = new HorizontalLayout(countryCode, number);
            layout.setFlexGrow(1.0, number);
            add(layout);
        }

        @Override
        protected String generateModelValue() {
            if (countryCode.getValue() != null && number.getValue() != null) {
                String s = countryCode.getValue() + " " + number.getValue();
                return s;
            }
            return "";
        }

        @Override
        protected void setPresentationValue(String phoneNumber) {
            String[] parts = phoneNumber != null ? phoneNumber.split(" ", 2) : new String[0];
            if (parts.length == 1) {
                countryCode.clear();
                number.setValue(parts[0]);
            } else if (parts.length == 2) {
                countryCode.setValue(parts[0]);
                number.setValue(parts[1]);
            } else {
                countryCode.clear();
                number.clear();
            }
        }

    }

}
