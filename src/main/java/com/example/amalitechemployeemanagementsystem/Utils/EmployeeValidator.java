package com.example.amalitechemployeemanagementsystem.Utils;

import com.example.amalitechemployeemanagementsystem.Exception.InvalidDepartmentException;
import com.example.amalitechemployeemanagementsystem.Exception.InvalidSalaryException;
import com.example.amalitechemployeemanagementsystem.model.Employee;

import java.util.Set;

public class EmployeeValidator {

    public static <T> void validateEmployee(Employee<T> employee, Set<String> validDepartments)
            throws InvalidSalaryException, InvalidDepartmentException {
        if (employee == null) {
            throw new IllegalArgumentException("Employee cannot be null");
        }

        if (employee.getName() == null || employee.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Employee name cannot be empty");
        }

        if (employee.getSalary() < 0) {
            throw new InvalidSalaryException("Salary cannot be negative: " + employee.getSalary());
        }

        if (employee.getDepartment() == null || employee.getDepartment().trim().isEmpty()) {
            throw new InvalidDepartmentException("Department cannot be empty");
        }

        // If validDepartments is not empty and the department is not in the list, it's invalid
        if (!validDepartments.isEmpty() && !validDepartments.contains(employee.getDepartment()) &&
                !employee.getDepartment().trim().isEmpty()) {
            // For new departments, we simply add them to valid departments
            validDepartments.add(employee.getDepartment());
        }
    }
}