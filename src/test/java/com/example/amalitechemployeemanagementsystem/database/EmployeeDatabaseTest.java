package com.example.amalitechemployeemanagementsystem.database;

import com.example.amalitechemployeemanagementsystem.Exception.EmployeeNotFoundException;
import com.example.amalitechemployeemanagementsystem.Exception.InvalidDepartmentException;
import com.example.amalitechemployeemanagementsystem.Exception.InvalidSalaryException;
import com.example.amalitechemployeemanagementsystem.model.Employee;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeDatabaseTest {
    private EmployeeDatabase<Integer> database;

    @BeforeEach
    void setUp() {
        database = new EmployeeDatabase<>();
    }

    @AfterEach
    void tearDown() {
        database = null;
    }

    @Test
    void addEmployee() throws InvalidSalaryException, InvalidDepartmentException, EmployeeNotFoundException {
        Employee<Integer> employee = new Employee<>(1, "John Doe", "IT", 75000, 4.5, 7, true);
        database.addEmployee(employee);

        assertNotNull(database.getEmployee(1));
        assertEquals("John Doe", database.getEmployee(1).getName());
    }

    @Test
    void removeEmployee() throws InvalidSalaryException, InvalidDepartmentException, EmployeeNotFoundException {
        Employee<Integer> employee = new Employee<>(1, "John Doe", "IT", 75000, 4.5, 7, true);
        database.addEmployee(employee);
        database.removeEmployee(1);

        assertThrows(EmployeeNotFoundException.class, () -> database.getEmployee(1));
    }

    @Test
    void updateEmployeeDetails() throws InvalidSalaryException, InvalidDepartmentException, EmployeeNotFoundException {
        Employee<Integer> employee = new Employee<>(1, "John Doe", "IT", 75000, 4.5, 7, true);
        database.addEmployee(employee);

        employee.setSalary(80000);
        database.updateEmployeeDetails(1, "salary", 80000.0);

        assertEquals(80000, database.getEmployee(1).getSalary());
    }

    @Test
    void searchByDepartment() throws InvalidSalaryException, InvalidDepartmentException {
        database.addEmployee(new Employee<>(1, "John Doe", "IT", 75000, 4.5, 7, true));
        database.addEmployee(new Employee<>(2, "Jane Smith", "HR", 65000, 4.8, 5, true));

        List<Employee<Integer>> itEmployees = database.searchByDepartment("IT");
        assertEquals(1, itEmployees.size());
        assertEquals("John Doe", itEmployees.getFirst().getName());
    }

    @Test
    void searchByName() throws InvalidSalaryException, InvalidDepartmentException {
        database.addEmployee(new Employee<>(1, "John Doe", "IT", 75000, 4.5, 7, true));

        List<Employee<Integer>> result = database.searchByName("John");
        assertEquals(1, result.size());
        assertEquals("John Doe", result.getFirst().getName());
    }

    @Test
    void filterByMinimumRating() throws InvalidSalaryException, InvalidDepartmentException {
        database.addEmployee(new Employee<>(1, "John Doe", "IT", 75000, 4.5, 7, true));
        database.addEmployee(new Employee<>(2, "Jane Smith", "HR", 65000, 3.5, 5, true));

        List<Employee<Integer>> filtered = database.filterByMinimumRating(4.0);
        assertEquals(1, filtered.size());
        assertEquals("John Doe", filtered.getFirst().getName());
    }

    @Test
    void filterBySalaryRange() throws InvalidSalaryException, InvalidDepartmentException {
        database.addEmployee(new Employee<>(1, "John Doe", "IT", 75000, 4.5, 7, true));
        database.addEmployee(new Employee<>(2, "Jane Smith", "HR", 65000, 4.8, 5, true));

        List<Employee<Integer>> filtered = database.filterBySalaryRange(70000, 80000);
        assertEquals(1, filtered.size());
        assertEquals("John Doe", filtered.getFirst().getName());
    }

    @Test
    void filterEmployees() throws InvalidSalaryException, InvalidDepartmentException {
        database.addEmployee(new Employee<>(1, "John Doe", "IT", 75000, 4.5, 7, true));
        database.addEmployee(new Employee<>(2, "Jane Smith", "HR", 65000, 4.8, 5, true));

        List<Employee<Integer>> filtered = database.filterEmployees(emp -> emp.getDepartment().equals("IT"));
        assertEquals(1, filtered.size());
        assertEquals("John Doe", filtered.getFirst().getName());
    }

    @Test
    void getEmployeeIterator() throws InvalidSalaryException, InvalidDepartmentException {
        database.addEmployee(new Employee<>(1, "John Doe", "IT", 75000, 4.5, 7, true));

        assertTrue(database.getEmployeeIterator().hasNext());
    }

    @Test
    void sortBySalary() throws InvalidSalaryException, InvalidDepartmentException {
        database.addEmployee(new Employee<>(1, "John Doe", "IT", 75000, 4.5, 7, true));
        database.addEmployee(new Employee<>(2, "Jane Smith", "HR", 65000, 4.8, 5, true));

        List<Employee<Integer>> sorted = database.sortBySalary();
        assertEquals(75000, sorted.getFirst().getSalary());
    }

    @Test
    void sortByPerformance() throws InvalidSalaryException, InvalidDepartmentException {
        database.addEmployee(new Employee<>(1, "John Doe", "IT", 75000, 4.5, 7, true));
        database.addEmployee(new Employee<>(2, "Jane Smith", "HR", 65000, 4.8, 5, true));

        List<Employee<Integer>> sorted = database.sortByPerformance();
        assertEquals(4.8, sorted.getFirst().getPerformanceRating());
    }

    @Test
    void sortByExperience() throws InvalidSalaryException, InvalidDepartmentException {
        database.addEmployee(new Employee<>(1, "John Doe", "IT", 75000, 4.5, 7, true));
        database.addEmployee(new Employee<>(2, "Jane Smith", "HR", 65000, 4.8, 5, true));

        List<Employee<Integer>> sorted = database.sortByExperience();
        assertEquals(7, sorted.getFirst().getYearsOfExperience());
    }

    @Test
    void giveSalaryRaise() throws InvalidSalaryException, InvalidDepartmentException, EmployeeNotFoundException {
        database.addEmployee(new Employee<>(1, "John Doe", "IT", 75000, 4.5, 7, true));

        int count = database.giveSalaryRaise(4.0, 10);
        assertEquals(1, count);
        assertEquals(82500, database.getEmployee(1).getSalary());
    }

    @Test
    void getTopPaidEmployees() throws InvalidSalaryException, InvalidDepartmentException {
        database.addEmployee(new Employee<>(1, "John Doe", "IT", 75000, 4.5, 7, true));
        database.addEmployee(new Employee<>(2, "Jane Smith", "HR", 65000, 4.8, 5, true));

        List<Employee<Integer>> topPaid = database.getTopPaidEmployees(1);
        assertEquals(75000, topPaid.getFirst().getSalary());
    }

    @Test
    void getAverageSalaryByDepartment() throws InvalidSalaryException, InvalidDepartmentException {
        database.addEmployee(new Employee<>(1, "John Doe", "IT", 75000, 4.5, 7, true));
        database.addEmployee(new Employee<>(2, "Jane Smith", "IT", 65000, 4.8, 5, true));

        double average = database.getAverageSalaryByDepartment("IT");
        assertEquals(70000, average);
    }

    @Test
    void getEmployee() throws InvalidSalaryException, InvalidDepartmentException, EmployeeNotFoundException {
        Employee<Integer> employee = new Employee<>(1, "John Doe", "IT", 75000, 4.5, 7, true);
        database.addEmployee(employee);

        Employee<Integer> retrieved = database.getEmployee(1);
        assertEquals("John Doe", retrieved.getName());
    }

    @Test
    void getEmployeeCount() throws InvalidSalaryException, InvalidDepartmentException {
        database.addEmployee(new Employee<>(1, "John Doe", "IT", 75000, 4.5, 7, true));

        assertEquals(1, database.getEmployeeCount());
    }

    @Test
    void getAllDepartments() throws InvalidDepartmentException {
        database.registerDepartment("IT");
        database.registerDepartment("HR");

        assertTrue(database.getAllDepartments().contains("IT"));
        assertTrue(database.getAllDepartments().contains("HR"));
    }

    @Test
    void registerDepartment() throws InvalidDepartmentException {
        database.registerDepartment("IT");

        assertTrue(database.getAllDepartments().contains("IT"));
    }
}