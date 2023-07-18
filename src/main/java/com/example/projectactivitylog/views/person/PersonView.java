package com.example.projectactivitylog.views.person;

import com.example.projectactivitylog.data.entity.SamplePerson;
import com.example.projectactivitylog.data.service.SamplePersonService;
import com.example.projectactivitylog.dto.PersonDto;
import com.example.projectactivitylog.dto.ProjectDto;
import com.example.projectactivitylog.servicess.PersonService;
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

@PageTitle("Person")
@Route(value = "person", layout = MainLayout.class)
@Uses(Icon.class)
public class PersonView extends Div {

    private TextField firstName = new TextField("First name");
    private TextField lastName = new TextField("Last name");

    private Button btnCancel = new Button("Cancel");
    private Button btnSave = new Button("Save");
    private Button btnNewPerson = new Button("New");
    private Button btnDelete = new Button("Delete");

    private Binder<PersonDto> binder = new Binder<>(PersonDto.class);

    public PersonView(PersonService personService) {
        addClassName("person-view");
        configureBinder();
        Grid<PersonDto> grid = new Grid<>(PersonDto.class, false);
        grid.addColumn(PersonDto::getId).setHeader("ID");
        grid.addColumn(PersonDto::getName).setHeader("First name");
        grid.addColumn(PersonDto::getSurname).setHeader("LastName");
        grid.setItems(personService.getAllPerson());
        grid.addSelectionListener(new SelectionListener<Grid<PersonDto>, PersonDto>() {
            @Override
            public void selectionChange(SelectionEvent<Grid<PersonDto>, PersonDto> selectionEvent) {
                Optional<PersonDto> firstSelectedItem = selectionEvent.getFirstSelectedItem();
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
            PersonDto personDto = binder.getBean();
            personService.updatePerson(personDto);
            grid.setItems(personService.getAllPerson());
            Notification.show("Details stored.");
            clearForm();
        });

        btnNewPerson.addClickListener(buttonClickEvent -> {
            PersonDto personDto = binder.getBean();
            personService.createNewPerson(personDto);
            grid.setItems(personService.getAllPerson());
            Notification.show("New person stored.");
            clearForm();
        });

        btnDelete.addClickListener(buttonClickEvent -> {
            PersonDto personDto = binder.getBean();
            personService.deletePerson(personDto.getId());
            grid.setItems(personService.getAllPerson());
            Notification.show("Person deleted.");
            clearForm();
        });
    }

    private void configureBinder() {
        binder.bind(firstName, PersonDto::getName, (PersonDto::setName));
        binder.bind(lastName, PersonDto::getSurname, (PersonDto::setSurname));
    }

    private void clearForm() {
        binder.setBean(new PersonDto());
    }

    private Component createTitle() {
        return new H3("Personal information");
    }

    private Component createFormLayout() {
        FormLayout formLayout = new FormLayout();
        formLayout.add(firstName, lastName);
        return formLayout;
    }

    private Component createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        btnSave.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(btnSave);
        buttonLayout.add(btnCancel);
        buttonLayout.add(btnNewPerson);
        buttonLayout.add(btnDelete);
        return buttonLayout;
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
