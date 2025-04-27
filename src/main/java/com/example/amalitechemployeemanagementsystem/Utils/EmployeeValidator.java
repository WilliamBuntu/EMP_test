package com.example.amalitechemployeemanagementsystem.Utils;

import com.example.amalitechemployeemanagementsystem.Exception.InvalidDepartmentException;
import com.example.amalitechemployeemanagementsystem.Exception.InvalidSalaryException;
import com.example.amalitechemployeemanagementsystem.model.Employee;

import java.util.Set;

public class EmployeeValidator {

    /**
     * Validates employee data
     * @param employee The employee to validate
     * @throws InvalidSalaryException If the salary is negative
     * @throws InvalidDepartmentException If the department is empty or invalid
     */
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



    /**
     * Validates employee data when updating an employee
     */
        public static void validateName(String name) {
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("Name cannot be empty");
            }
        }

        public static void validateDepartment(String department) throws InvalidDepartmentException {
            if (department == null || department.trim().isEmpty()) {
                throw new InvalidDepartmentException("Department cannot be empty");
            }
        }

        public static void validateSalary(Double salary) throws InvalidSalaryException {
            if (salary == null || salary < 0) {
                throw new InvalidSalaryException("Salary cannot be negative: " + salary);
            }
        }

        public static void validatePerformanceRating(Double rating) {
            if (rating == null || rating < 0 || rating > 5) {
                throw new IllegalArgumentException("Performance rating must be between 0 and 5: " + rating);
            }
        }

        public static void validateYearsOfExperience(Integer years) {
            if (years == null || years < 0) {
                throw new IllegalArgumentException("Years of experience cannot be negative: " + years);
            }
        }
    }
