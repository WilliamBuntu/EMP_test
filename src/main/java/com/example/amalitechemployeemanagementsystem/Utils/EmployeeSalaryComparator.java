package com.example.amalitechemployeemanagementsystem.Utils;

import com.example.amalitechemployeemanagementsystem.model.Employee;

import java.util.Comparator;

/**
  Comparator to sort employees by salary in descending order. @param <T> Type of the employee ID
 */

public class EmployeeSalaryComparator<T> implements Comparator<Employee<T>> {
    @Override
    public int compare(Employee<T> e1, Employee<T> e2) {
        // Handle null objects
        if (e1 == null && e2 == null) return 0;
        if (e1 == null) return 1; // Null employees go at the end
        if (e2 == null) return -1;

        // Handle null salary values
        Double salary1 = e1.getSalary();
        Double salary2 = e2.getSalary();

        if (salary1 == null && salary2 == null) return 0;
        if (salary1 == null) return 1; // Null salaries go at the end
        if (salary2 == null) return -1;

        // Sort by salary in descending order (highest first)
        return Double.compare(salary2, salary1);
    }
}