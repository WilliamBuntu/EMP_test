package com.example.amalitechemployeemanagementsystem.database;

import com.example.amalitechemployeemanagementsystem.Exception.InvalidDepartmentException;
import com.example.amalitechemployeemanagementsystem.Exception.InvalidSalaryException;
import com.example.amalitechemployeemanagementsystem.Utils.EmployeePerformanceComparator;
import com.example.amalitechemployeemanagementsystem.Utils.EmployeeSalaryComparator;
import com.example.amalitechemployeemanagementsystem.Utils.EmployeeValidator;
import com.example.amalitechemployeemanagementsystem.model.Employee;

import java.util.*;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class EmployeeDatabase <T> {
  private final Map<T, Employee<T>> employees;
    private final Set<String> validDepartments;
    private static final Logger logger = Logger.getLogger(EmployeeDatabase.class.getName());


    public EmployeeDatabase(){
      employees = new HashMap<>();
      validDepartments = new HashSet<>();
  }

  // method that Adds a new employee to the database
    // the method returns  true if the employee was added successfully, false if an employee with the same ID already exists

    public void addEmployee(Employee<T> employee) throws InvalidSalaryException, InvalidDepartmentException {
        try {
            // Validate employee data
            EmployeeValidator.validateEmployee(employee, validDepartments);

            if(employees.containsKey(employee.employeeId())) {
                logger.warning("Employee with ID " + employee.employeeId() + " already exists. Update skipped.");
                return;
            }

            employees.put(employee.employeeId(), employee);
            validDepartments.add(employee.getDepartment());
            logger.info("Employee added successfully: " + employee.getName());
        } catch (NullPointerException e) {
            logger.log(Level.SEVERE, "Failed to add employee: Null employee object", e);
            throw new IllegalArgumentException("Employee cannot be null", e);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unexpected error while adding employee", e);
            throw e;
        }
    }



    /**
     * Validates employee data
     * @param employee The employee to validate
     * @throws InvalidSalaryException If the salary is negative
     * @throws InvalidDepartmentException If the department is empty or invalid
     */


   // method to Removes an employee from the database.
    // It returns true if the employee was removed successfully, false if no employee with the given ID exists
   public void removeEmployee(T employeeId){
      if(!employees.containsKey(employeeId)){
          return;
      }

      employees.remove(employeeId);
   }

    //Updates an employee's details.
   public void updateEmployeeDetails(T employeeId, String field, Object newValue){
      if (!employees.containsKey(employeeId)) return;

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
          }

      } catch (ClassCastException _) {
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

    /**
     * Gives a salary raise to employees with high performance ratings.
     * @param minRating The minimum performance rating required for a raise
     * @param percentageRaise The percentage salary raise
     * @return The number of employees who received a raise
     */
    public int giveSalaryRaise(double minRating, double percentageRaise) {
        int count = 0;
        for (Employee<T> employee : employees.values()) {
            if (employee.getPerformanceRating() >= minRating) {
                double newSalary = employee.getSalary() * (1 + percentageRaise / 100);
                employee.setSalary(newSalary);
                count++;
            }
        }
        return count;
    }

    /**
     * Retrieves the top N highest-paid employees.
     * @param n The number of top employees to retrieve
     * @return A list of the top N highest-paid employees
     */
    public List<Employee<T>> getTopPaidEmployees(int n) {
        return employees.values().stream()
                .sorted(new EmployeeSalaryComparator<>())
                .limit(n)
                .collect(Collectors.toList());
    }

    /**
     * Calculates the average salary of employees in a specific department.
     * @param department The department to calculate the average salary for
     * @return The average salary, or 0 if no employees in the department
     */
    public double getAverageSalaryByDepartment(String department) {
        return employees.values().stream()
                .filter(emp -> emp.getDepartment().equalsIgnoreCase(department))
                .mapToDouble(Employee::getSalary)
                .average()
                .orElse(0);
    }

    /**
     * Gets an employee by their ID.
     * @param employeeId The ID of the employee to retrieve
     * @return The employee, or null if no employee with the given ID exists
     */
    public Employee<T> getEmployee(T employeeId) {
        return employees.get(employeeId);
    }

    /**
     * Gets the number of employees.
     * @return The number of employees
     */
    public int getEmployeeCount() {
        return employees.size();
    }

    //displaying available departments
    public Set<String> getAllDepartments() {
        Set<String> departments = new HashSet<>();
        for (Employee<T> employee : employees.values()) {
            departments.add(employee.getDepartment());
        }
        return departments;
    }

}
