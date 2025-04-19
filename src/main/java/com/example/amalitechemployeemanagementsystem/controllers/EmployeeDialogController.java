package com.example.amalitechemployeemanagementsystem.controllers;

import com.example.amalitechemployeemanagementsystem.model.Employee;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

//Controller for the employee dialog.

public class EmployeeDialogController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField departmentField;

    @FXML
    private TextField salaryField;

    @FXML
    private TextField ratingField;

    @FXML
    private TextField experienceField;

    @FXML
    private CheckBox activeCheckBox;

    /**
     * Sets the dialog fields with the employee data for editing.
     *
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
     *
     * @param employeeId The ID to assign to the employee
     * @return The created Employee object, or null if validation fails
     */
    public Employee<Integer> getEmployee(Integer employeeId) {
        try {
            String name = nameField.getText().trim();
            String department = departmentField.getText().trim();
            double salary = Double.parseDouble(salaryField.getText().trim());
            double rating = Double.parseDouble(ratingField.getText().trim());
            int experience = Integer.parseInt(experienceField.getText().trim());
            boolean active = activeCheckBox.isSelected();

            return new Employee<>(employeeId, name, department, salary, rating, experience, active);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Please check your input");
            alert.setContentText("Salary, rating, and experience must be numbers");
            alert.showAndWait();
            return null;
        }
    }
}