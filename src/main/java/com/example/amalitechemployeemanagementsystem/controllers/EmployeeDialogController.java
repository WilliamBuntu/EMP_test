package com.example.amalitechemployeemanagementsystem.controllers;

import com.example.amalitechemployeemanagementsystem.model.Employee;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class EmployeeDialogController {

    @FXML
    private TextField nameField;
    @FXML
    private Text nameError;

    @FXML
    private TextField departmentField;
    @FXML
    private Text departmentError;

    @FXML
    private TextField salaryField;
    @FXML
    private Text salaryError;

    @FXML
    private TextField ratingField;
    @FXML
    private Text ratingError;

    @FXML
    private TextField experienceField;
    @FXML
    private Text experienceError;

    @FXML
    private CheckBox activeCheckBox;

    @FXML
    private DialogPane rootPane;

    /**
     * Initialize the controller and set up validation listeners.
     */
    @FXML
    public void initialize() {
        // Initialize all error texts
        nameError.setVisible(false);
        departmentError.setVisible(false);
        salaryError.setVisible(false);
        ratingError.setVisible(false);
        experienceError.setVisible(false);

        // Set up validation listeners
        setupValidationListeners();

        // Add validation before dialog closes on OK button
        Button okButton = (Button) rootPane.lookupButton(ButtonType.OK);
        okButton.addEventFilter(javafx.event.ActionEvent.ACTION, event -> {
            if (!validateAllFields()) {
                // Prevent the dialog from closing if validation fails
                event.consume();
            }
        });
    }

    private void setupValidationListeners() {
        // Add focus lost listeners for validation
        nameField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) { // Focus lost
                validateName();
            }
        });

        departmentField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                validateDepartment();
            }
        });

        // Real-time validation as user types for numeric fields
        salaryField.textProperty().addListener((obs, oldVal, newVal) -> {
            validateSalary();
            updateFieldStyle(salaryField, salaryError.isVisible());
        });

        ratingField.textProperty().addListener((obs, oldVal, newVal) -> {
            validateRating();
            updateFieldStyle(ratingField, ratingError.isVisible());
        });

        experienceField.textProperty().addListener((obs, oldVal, newVal) -> {
            validateExperience();
            updateFieldStyle(experienceField, experienceError.isVisible());
        });
    }

    private void updateFieldStyle(TextField field, boolean hasError) {
        if (hasError) {
            field.getStyleClass().add("error");
        } else {
            field.getStyleClass().removeAll("error");
        }
    }

    private boolean validateName() {
        String name = nameField.getText().trim();
        if (name.isEmpty()) {
            nameError.setText("Name cannot be empty");
            nameError.setVisible(true);
            updateFieldStyle(nameField, true);
            return false;
        }
        nameError.setVisible(false);
        updateFieldStyle(nameField, false);
        return true;
    }

    private boolean validateDepartment() {
        String department = departmentField.getText().trim();
        if (department.isEmpty()) {
            departmentError.setText("Department cannot be empty");
            departmentError.setVisible(true);
            updateFieldStyle(departmentField, true);
            return false;
        }

        if (!department.matches("[a-zA-Z]+")) {
            departmentError.setText("Department must contain only letters");
            departmentError.setVisible(true);
            updateFieldStyle(departmentField, true);
            return false;
        }

        departmentError.setVisible(false);
        updateFieldStyle(departmentField, false);
        return true;
    }

    private boolean validateSalary() {
        String salaryText = salaryField.getText().trim();
        if (salaryText.isEmpty()) {
            salaryError.setText("Salary cannot be empty");
            salaryError.setVisible(true);
            return false;
        }

        try {
            double salary = Double.parseDouble(salaryText);
            if (salary < 0) {
                salaryError.setText("Salary cannot be negative");
                salaryError.setVisible(true);
                return false;
            }
            salaryError.setVisible(false);
            return true;
        } catch (NumberFormatException e) {
            salaryError.setText("Please enter a valid number");
            salaryError.setVisible(true);
            return false;
        }
    }

    private boolean validateRating() {
        String ratingText = ratingField.getText().trim();
        if (ratingText.isEmpty()) {
            ratingError.setText("Rating cannot be empty");
            ratingError.setVisible(true);
            return false;
        }

        try {
            double rating = Double.parseDouble(ratingText);
            if (rating < 1 || rating > 5) {
                ratingError.setText("Rating must be between 1 and 5");
                ratingError.setVisible(true);
                return false;
            }
            ratingError.setVisible(false);
            return true;
        } catch (NumberFormatException e) {
            ratingError.setText("Please enter a valid number");
            ratingError.setVisible(true);
            return false;
        }
    }

    private boolean validateExperience() {
        String experienceText = experienceField.getText().trim();
        if (experienceText.isEmpty()) {
            experienceError.setText("Years of experience cannot be empty");
            experienceError.setVisible(true);
            return false;
        }

        try {
            int experience = Integer.parseInt(experienceText);
            if (experience < 0) {
                experienceError.setText("Years of experience cannot be negative");
                experienceError.setVisible(true);
                return false;
            }
            experienceError.setVisible(false);
            return true;
        } catch (NumberFormatException e) {
            experienceError.setText("Please enter a valid whole number");
            experienceError.setVisible(true);
            return false;
        }
    }

    /**
     * Validates all fields at once.
     * @return true if all fields are valid, false otherwise
     */
    private boolean validateAllFields() {
        boolean nameValid = validateName();
        boolean departmentValid = validateDepartment();
        boolean salaryValid = validateSalary();
        boolean ratingValid = validateRating();
        boolean experienceValid = validateExperience();

        return nameValid && departmentValid && salaryValid && ratingValid && experienceValid;
    }

    /**
     * Sets the dialog fields with the employee data for editing.
     * @param employee The employee to edit
     */
    public void setEmployee(Employee<Integer> employee) {
        nameField.setText(employee.getName());
        departmentField.setText(employee.getDepartment());
        salaryField.setText(String.valueOf(employee.getSalary()));
        ratingField.setText(String.valueOf(employee.getPerformanceRating()));
        experienceField.setText(String.valueOf(employee.getYearsOfExperience()));
        activeCheckBox.setSelected(employee.isActive());
    }

    /**
     * Creates an Employee object from the dialog fields.
     * @param employeeId The ID to assign to the employee
     * @return The created Employee object, or null if validation fails
     */
    public Employee<Integer> getEmployee(Integer employeeId) {
        // Final validation check
        if (!validateAllFields()) {
            return null;
        }

        String name = nameField.getText().trim();
        String department = departmentField.getText().trim();
        double salary = Double.parseDouble(salaryField.getText().trim());
        double rating = Double.parseDouble(ratingField.getText().trim());
        int experience = Integer.parseInt(experienceField.getText().trim());
        boolean active = activeCheckBox.isSelected();

        return new Employee<>(employeeId, name, department, salary, rating, experience, active);
    }
}