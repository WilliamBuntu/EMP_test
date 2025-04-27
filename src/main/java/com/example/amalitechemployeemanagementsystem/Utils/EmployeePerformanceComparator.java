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
        // Handle null objects
        if (e1 == null && e2 == null) return 0;
        if (e1 == null) return 1; // Null employees go at the end
        if (e2 == null) return -1;

        // Handle null performance values
        Double perf1 = e1.getPerformanceRating();
        Double perf2 = e2.getPerformanceRating();

        if (perf1 == null && perf2 == null) return 0;
        if (perf1 == null) return 1; // Null ratings go at the end
        if (perf2 == null) return -1;

        // Sort by performance in descending order (highest first)
        return Double.compare(perf2, perf1);
    }
}