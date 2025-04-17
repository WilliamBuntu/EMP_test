package com.example.amalitechemployeemanagementsystem.database;

import com.example.amalitechemployeemanagementsystem.Utils.EmployeePerformanceComparator;
import com.example.amalitechemployeemanagementsystem.Utils.EmployeeSalaryComparator;
import com.example.amalitechemployeemanagementsystem.model.Employee;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class EmployeeDatabase <T> {
  private final Map<T, Employee<T>> employees;

  public EmployeeDatabase(){
      employees = new HashMap<>();
  }

  // method that Adds a new employee to the database
    // the method returns  true if the employee was added successfully, false if an employee with the same ID already exists

   public boolean addEmployee(Employee<T> employee){
      if(employees.containsKey(employee.getemployeeId())){
          return false;
      }

      employees.put(employee.getemployeeId(),employee);

      return true;
   }


   // method to Removes an employee from the database.
    // It returns true if the employee was removed successfully, false if no employee with the given ID exists
   public boolean removeEmployee(T employeeId){
      if(!employees.containsKey(employeeId)){
          return false;
      }

      employees.remove(employeeId);
      return true;
   }

    //Updates an employee's details.
   public boolean updateEmployeeDetails(T employeeId, String field, Object newValue){
      if (!employees.containsKey(employeeId)) return false;

      Employee<T> employee = employees.get(employeeId);

      try {

          switch (field.toLowerCase()){
              case "name":
                  employee.setName((String) newValue);
                  break;
              case "department":
                  employee.setDepartment((String) newValue);
                  break;
              case "salary":
                  employee.setSalary((Double) newValue);
                  break;
              case "performancerating":
                  employee.setPerformanceRating((Double) newValue);
                  break;
              case "yearsofexperience":
                  employee.setYearsOfExperience((Integer) newValue);
                  break;
              case "isactive":
                  employee.setActive((Boolean) newValue);
                  break;
              default:
                  return false;
          }

          return true;

      } catch (ClassCastException e) {
          return false;
      }
   }

   //Retrieves all employees.
   public List<Employee<T>> getAllEmployees() {
       return new ArrayList<>(employees.values());
   }

   // Searches for employees by department.

    public List<Employee<T>> searchByDepartment(String department){
      return employees.values().stream()
              .filter(emp -> emp.getDepartment().equalsIgnoreCase(department))
              .collect(Collectors.toList());
    }

    //Searches for employees by name (partial match).
    public List<Employee<T>> searchByName(String name){
      return employees.values().stream()
              .filter(emp -> emp.getName().toLowerCase().contains(name.toLowerCase()))
              .collect(Collectors.toList());
    }

    // Filters employees by minimum performance rating.
    public List<Employee<T>> filterByMinimumRating(double minRating){
      return employees.values().stream()
              .filter(emp -> emp.getPerformanceRating()>= minRating)
              .collect(Collectors.toList());
    }

    // Filters employees by salary range.
    public List<Employee<T>> filterBySalaryRange(double minSalary , double maxSalary){
      return  employees.values().stream()
              .filter(emp -> emp.getSalary()>= minSalary && emp.getSalary() <= maxSalary)
              .collect(Collectors.toList());
    }

    // Custom filtering method using a predicate.
    public List<Employee<T>> filterEmployees(Predicate<Employee<T>> predicate){
      return employees.values().stream()
              .filter(predicate)
              .collect(Collectors.toList());
    }

    // Gets an iterator for all employees. and @return An iterator for the employees
    public Iterator<Employee<T>> getEmployeeIterator() {
        return employees.values().iterator();
    }

    //Sorts employees by salary (highest first).
    public List<Employee<T>> sortBySalary() {
        List<Employee<T>> sortedEmployees = new ArrayList<>(employees.values());
        sortedEmployees.sort(new EmployeeSalaryComparator<>());
        return sortedEmployees;
    }

    //Sorts employees by performance rating (the best first).
    public List<Employee<T>> sortByPerformance(){
      List<Employee<T>> sortedEmployees = new ArrayList<>(employees.values());
      sortedEmployees.sort(new EmployeePerformanceComparator<>());
      return sortedEmployees;
    }

    /**
     * Sorts employees by years of experience (most experienced first).
     * @return A list of employees sorted by years of experience in descending order
     */
    public List<Employee<T>> sortByExperience() {
        List<Employee<T>> sortedEmployees = new ArrayList<>(employees.values());
        Collections.sort(sortedEmployees); // Uses the natural ordering defined by compareTo
        return sortedEmployees;
    }

}
