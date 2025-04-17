package com.example.amalitechemployeemanagementsystem.model;

import java.util.Objects;

public class Employee<T> implements Comparable<Employee<T>> {
private  T employeeId;
private String name;
private String department;
private double salary;
private double performanceRating;
private int yearsOfExperience;
private  boolean isActive;

// Constructor to create Employee object

    public Employee(T employeeId, String name, String department,double salary, double performanceRating, int yearsOfExperience, boolean isActive){
        this.employeeId = employeeId;
        this.name = name;
        this.department = department;
        this.salary = salary;
        this.performanceRating = performanceRating;
        this.yearsOfExperience = yearsOfExperience;
        this.isActive = isActive;

    }

    // setters And getters

    public T getemployeeId(){
        return employeeId;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
      this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double getPerformanceRating() {
        return performanceRating;
    }

    public void setPerformanceRating(double performanceRating) {
        this.performanceRating = performanceRating;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }


// Implementing the compareTo method from Comparable interface and
// Sorts employees by years of experience in descending order.

    @Override
    public int compareTo(Employee<T> other) {
        return Integer.compare(other.yearsOfExperience, this.yearsOfExperience);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee<?> employee = (Employee<?>) o;
        return Objects.equals(employeeId, employee.employeeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId);
    }

    @Override
    public String toString() {
        return String.format("Employee ID: %s | Name: %s | Department: %s | Salary: $%.2f | " +
                        "Performance: %.1f | Experience: %d years | Active: %s",
                employeeId, name, department, salary,
                performanceRating, yearsOfExperience, isActive);
    }
}
