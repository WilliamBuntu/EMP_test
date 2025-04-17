package com.example.amalitechemployeemanagementsystem.Utils;

import com.example.amalitechemployeemanagementsystem.model.Employee;

import java.util.Comparator;

/**
 * Comparator to sort employees by performance rating in descending order.
 *
 * @param <T> Type of the employee ID
 */
public class EmployeePerformanceComparator<T> implements Comparator<Employee<T>> {
    @Override
    public int compare(Employee<T> e1, Employee<T> e2) {
        return Double.compare(e2.getPerformanceRating(), e1.getPerformanceRating());
    }
}