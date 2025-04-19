package com.example.amalitechemployeemanagementsystem.demo;

import com.example.amalitechemployeemanagementsystem.database.EmployeeDatabase;
import com.example.amalitechemployeemanagementsystem.model.Employee;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Formatter;
import java.util.ArrayList;

/*
 * Demo class to show how to use the Employee Management System without the GUI.
 */
public class EmployeeManagementDemo {

    public static void main(String[] args) {
        // Create a new employee database
        EmployeeDatabase<Integer> database = new EmployeeDatabase<>();

        // Add some employees
        System.out.println("Adding employees...");
        database.addEmployee(new Employee<>(1, "John Doe", "IT", 75000, 4.5, 7, true));
        database.addEmployee(new Employee<>(2, "Jane Smith", "HR", 65000, 4.8, 5, true));
        database.addEmployee(new Employee<>(3, "Bob Johnson", "Finance", 85000, 4.2, 10, true));
        database.addEmployee(new Employee<>(4, "Alice Brown", "IT", 70000, 4.7, 6, true));
        database.addEmployee(new Employee<>(5, "Charlie Davis", "Marketing", 68000, 3.9, 4, true));
        database.addEmployee(new Employee<>(6, "Eva Wilson", "HR", 62000, 4.1, 3, true));
        database.addEmployee(new Employee<>(7, "Frank Miller", "Finance", 90000, 4.6, 12, true));

        // Display all employees
        System.out.println("\n--- All Employees ---");
        displayEmployees(database.getAllEmployees());

        // Search by department
        System.out.println("\n--- Employees in IT Department ---");
        displayEmployees(database.searchByDepartment("IT"));

        // Search by name
        System.out.println("\n--- Employees with 'Smith' in their name ---");
        displayEmployees(database.searchByName("Smith"));

        // Filter by minimum rating
        System.out.println("\n--- Employees with rating >= 4.5 ---");
        displayEmployees(database.filterByMinimumRating(4.5));

        // Filter by salary range
        System.out.println("\n--- Employees with salary between FRW 65,000 and FRW 80,000 ---");
        displayEmployees(database.filterBySalaryRange(65000, 80000));

        // Sort by experience (natural ordering)
        System.out.println("\n--- Employees sorted by experience (descending) ---");
        displayEmployees(database.sortByExperience());

        // Sort by salary
        System.out.println("\n--- Employees sorted by salary (highest first) ---");
        displayEmployees(database.sortBySalary());

        // Sort by performance
        System.out.println("\n--- Employees sorted by performance rating (best first) ---");
        displayEmployees(database.sortByPerformance());

        // Get top paid employees
        System.out.println("\n--- Top 3 highest-paid employees ---");
        displayEmployees(database.getTopPaidEmployees(3));

        // Calculate average salary by department
        String department = "Finance";
        double avgSalary = database.getAverageSalaryByDepartment(department);
        System.out.printf("\nAverage salary in %s department: FRW %.2f\n", department, avgSalary);

        // Give salary raise to high performers
        System.out.println("\n--- Giving 5% raise to employees with rating >= 4.5 ---");
        int raisedCount = database.giveSalaryRaise(4.5, 5.0);
        System.out.printf("%d employees received a raise\n", raisedCount);

        // Display updated employees
        System.out.println("\n--- Updated Employee List ---");
        displayEmployees(database.getAllEmployees());

        // Using the iterator
        System.out.println("\n--- Using Iterator to display employees ---");
        Iterator<Employee<Integer>> iterator = database.getEmployeeIterator();
        List<Employee<Integer>> employeeListFromIterator = new ArrayList<>();
        while (iterator.hasNext()) {
            employeeListFromIterator.add(iterator.next());
        }
        displayEmployees(employeeListFromIterator);

        // Update an employee
        System.out.println("\n--- Updating employee with ID 3 ---");
        database.updateEmployeeDetails(3, "salary", 90000.0);
        database.updateEmployeeDetails(3, "performanceRating", 4.7);
        System.out.println("Updated employee: " + database.getEmployee(3));

        // Remove an employee
        System.out.println("\n--- Removing employee with ID 5 ---");
        database.removeEmployee(5);
        System.out.println("Employee count after removal: " + database.getEmployeeCount());

        Set<String> uniqueDepartments = database.getAllDepartments(); // Assuming you have an instance of EmployeeDatabase

        System.out.println("List of available departments  Departments:");
        for (String department1 : uniqueDepartments) {
            System.out.println("- " + department1);
        }
    }
    

    // a method to display a list of employees in a table format.
    private static <T> void displayEmployees(List<Employee<T>> employees) {
        if (employees.isEmpty()) {
            System.out.println("No employees found.");
            return;
        }

        // Print table header
        System.out.printf("%-5s %-20s %-15s %-10s %-10s %-10s %-10s\n", 
                          "ID", "Name", "Department", "Salary", "Rating", "Experience", "Active");
        System.out.println("--------------------------------------------------------------------------------");

        // Print each employee in a row
        for (Employee<T> employee : employees) {
            System.out.printf("%-5s %-20s %-15s %-10.2f %-10.1f %-10d %-10s\n",
                              employee.getemployeeId(),
                              employee.getName(),
                              employee.getDepartment(),
                              employee.getSalary(),
                              employee.getPerformanceRating(),
                              employee.getYearsOfExperience(),
                              employee.isActive() ? "Yes" : "No");
        }
    }
}

