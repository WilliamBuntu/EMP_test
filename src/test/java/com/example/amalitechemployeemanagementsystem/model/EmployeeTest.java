package com.example.amalitechemployeemanagementsystem.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    private Employee<Integer> employee;

    @BeforeEach
    void setUp() {
        employee = new Employee<>(1, "John Doe", "IT", 50000, 4.5, 5, true);
    }

    @AfterEach
    void tearDown() {
        employee = null;
    }

    @Test
    void employeeId() {
        assertEquals(1, employee.employeeId());
    }

    @Test
    void getName() {
        assertEquals("John Doe", employee.getName());
    }

    @Test
    void setName() {
        employee.setName("Jane Doe");
        assertEquals("Jane Doe", employee.getName());
    }

    @Test
    void getDepartment() {
        assertEquals("IT", employee.getDepartment());
    }

    @Test
    void setDepartment() {
        employee.setDepartment("HR");
        assertEquals("HR", employee.getDepartment());
    }

    @Test
    void getSalary() {
        assertEquals(50000, employee.getSalary());
    }

    @Test
    void setSalary() {
        employee.setSalary(60000);
        assertEquals(60000, employee.getSalary());
    }

    @Test
    void getPerformanceRating() {
        assertEquals(4.5, employee.getPerformanceRating());
    }

    @Test
    void setPerformanceRating() {
        employee.setPerformanceRating(4.8);
        assertEquals(4.8, employee.getPerformanceRating());
    }

    @Test
    void getYearsOfExperience() {
        assertEquals(5, employee.getYearsOfExperience());
    }

    @Test
    void setYearsOfExperience() {
        employee.setYearsOfExperience(6);
        assertEquals(6, employee.getYearsOfExperience());
    }

    @Test
    void isActive() {
        assertTrue(employee.isActive());
    }

    @Test
    void setActive() {
        employee.setActive(false);
        assertFalse(employee.isActive());
    }

    @Test
    void testCompareTo() {
        Employee<Integer> employee2 = new Employee<>(2, "Jane Doe", "HR", 60000, 4.8, 7, true);
        assertFalse(employee.compareTo(employee2) < 0);
        assertTrue(employee2.compareTo(employee) > 0);
    }

    @Test
    void testEquals() {
        Employee<Integer> employee2 = new Employee<>(1, "Jane Doe", "HR", 60000, 4.8, 7, true);
        Employee<Integer> employee3 = new Employee<>(2, "Jane Doe", "HR", 60000, 4.8, 7, true);
        assertEquals(employee, employee2);
        assertNotEquals(employee, employee3);
    }

    @Test
    void testHashCode() {
        Employee<Integer> employee2 = new Employee<>(1, "Jane Doe", "HR", 60000, 4.8, 7, true);
        assertEquals(employee.hashCode(), employee2.hashCode());
    }

    @Test
    void testToString() {
        String expected = "Employee ID: 1 | Name: John Doe | Department: IT | Salary: FRW 50000.00 | " +
                "Performance: 4.5 | Experience: 5 years | Active: true";
        assertEquals(expected, employee.toString());
    }
}