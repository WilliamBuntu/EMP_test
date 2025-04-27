package com.example.amalitechemployeemanagementsystem.database;

import com.example.amalitechemployeemanagementsystem.Exception.EmployeeNotFoundException;
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



   // method to Removes an employee from the database.
   public void removeEmployee(T employeeId) throws EmployeeNotFoundException {


      try {
          if (employeeId == null) {
              throw new IllegalArgumentException("Employee ID cannot be null");
          }

            if (!employees.containsKey(employeeId)) {
                logger.warning("Employee with ID " + employeeId + " not found. Removal skipped.");
                throw new EmployeeNotFoundException("Employee with ID " + employeeId + " not found.");
            }
            Employee<T> removedEmployee = employees.remove(employeeId);
            logger.info("Employee removed successfully: " + removedEmployee.getName());
      }catch (Exception e) {
          logger.log(Level.SEVERE, "Unexpected error while removing employee", e);
          throw e;
      }
   }

    //Updates an employee's details.
 public void updateEmployeeDetails(T employeeId, String field, Object newValue)
         throws EmployeeNotFoundException, InvalidSalaryException, InvalidDepartmentException {
     try {
         if (employeeId == null) {
             throw new IllegalArgumentException("Employee ID cannot be null");
         }

         if (field == null || field.trim().isEmpty()) {
             throw new IllegalArgumentException("Field name cannot be null or empty");
         }

         if (!employees.containsKey(employeeId)) {
             throw new EmployeeNotFoundException("No employee found with ID: " + employeeId);
         }

         Employee<T> employee = employees.get(employeeId);

         switch (field.toLowerCase()) {
             case "name":
                 EmployeeValidator.validateName((String) newValue);
                 employee.setName((String) newValue);
                 break;
             case "department":
                 EmployeeValidator.validateDepartment((String) newValue);
                 employee.setDepartment((String) newValue);
                 validDepartments.add((String) newValue);
                 break;
             case "salary":
                 EmployeeValidator.validateSalary((Double) newValue);
                 employee.setSalary((Double) newValue);
                 break;
             case "performancerating":
                 EmployeeValidator.validatePerformanceRating((Double) newValue);
                 employee.setPerformanceRating((Double) newValue);
                 break;
             case "yearsofexperience":
                 EmployeeValidator.validateYearsOfExperience((Integer) newValue);
                 employee.setYearsOfExperience((Integer) newValue);
                 break;
             case "isactive":
                 employee.setActive((Boolean) newValue);
                 break;
             default:
                 throw new IllegalArgumentException("Unknown field: " + field);
         }

         logger.info("Employee updated successfully: " + employee.getName() + ", Field: " + field);
     } catch (ClassCastException e) {
         logger.log(Level.WARNING, "Invalid value type for field: " + field, e);
         throw new IllegalArgumentException("Invalid value type for field: " + field, e);
     } catch (Exception e) {
         logger.log(Level.SEVERE, "Error updating employee details", e);
         throw e;
     }
 }
    //Retrieves all employees.
   public List<Employee<T>> getAllEmployees() {

       try {
           return new ArrayList<>(employees.values());
       } catch (Exception e) {
           logger.log(Level.SEVERE, "Error retrieving all employees", e);
           throw e;
       }
   }

   // Searches for employees by department.

    public List<Employee<T>> searchByDepartment(String department) throws InvalidDepartmentException {
        try {
            if (department == null || department.trim().isEmpty()) {
                throw new InvalidDepartmentException("Department cannot be null or empty");
            }

            return employees.values().stream()
                    .filter(emp -> emp.getDepartment().equalsIgnoreCase(department))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error searching employees by department", e);
            throw e;
        }
    }
    //Searches for employees by name (partial match).

    /**
     * Searches for employees by name (partial match)
     * @param name The name to search for
     * @return A list of employees with matching names
     * @throws IllegalArgumentException If the name is null or empty
     */
    public List<Employee<T>> searchByName(String name) throws IllegalArgumentException {
        try {
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("Name cannot be null or empty");
            }

            return employees.values().stream()
                    .filter(emp -> emp.getName().toLowerCase().contains(name.toLowerCase()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error searching employees by name", e);
            throw e;
        }
    }

    /**
     * Filters employees by minimum performance rating
     * @param minRating The minimum rating to filter by
     * @return A list of employees with ratings greater than or equal to the minimum
     * @throws IllegalArgumentException If the rating is negative
     */
    public List<Employee<T>> filterByMinimumRating(double minRating) throws IllegalArgumentException {
        try {
            if (minRating < 0) {
                throw new IllegalArgumentException("Minimum rating cannot be negative");
            }

            return employees.values().stream()
                    .filter(emp -> emp.getPerformanceRating() >= minRating)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error filtering employees by minimum rating", e);
            throw e;
        }
    }

    /**
     * Filters employees by salary range
     * @param minSalary The minimum salary
     * @param maxSalary The maximum salary
     * @return A list of employees with salaries in the specified range
     * @throws InvalidSalaryException If minSalary is negative or maxSalary is less than minSalary
     */
    public List<Employee<T>> filterBySalaryRange(double minSalary, double maxSalary) throws InvalidSalaryException {
        try {
            if (minSalary < 0) {
                throw new InvalidSalaryException("Minimum salary cannot be negative: " + minSalary);
            }

            if (maxSalary < minSalary) {
                throw new IllegalArgumentException("Maximum salary cannot be less than minimum salary");
            }

            return employees.values().stream()
                    .filter(emp -> emp.getSalary() >= minSalary && emp.getSalary() <= maxSalary)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error filtering employees by salary range", e);
            throw e;
        }
    }

    /**
     * Custom filtering method using a predicate
     * @param predicate The predicate to filter by
     * @return A list of employees that satisfy the predicate
     * @throws IllegalArgumentException If the predicate is null
     */
    public List<Employee<T>> filterEmployees(Predicate<Employee<T>> predicate) throws IllegalArgumentException {
        try {
            if (predicate == null) {
                throw new IllegalArgumentException("Predicate cannot be null");
            }

            return employees.values().stream()
                    .filter(predicate)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error filtering employees with custom predicate", e);
            throw e;
        }
    }

    /**
     * Gets an iterator for all employees
     * @return An iterator for the employees
     */
    public Iterator<Employee<T>> getEmployeeIterator() {
        try {
            return employees.values().iterator();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error getting employee iterator", e);
            throw e;
        }
    }

    /**
     * Sorts employees by salary (highest first)
     * @return A list of employees sorted by salary in descending order
     */
    public List<Employee<T>> sortBySalary() {
        try {
            List<Employee<T>> sortedEmployees = new ArrayList<>(employees.values());
            sortedEmployees.sort(new EmployeeSalaryComparator<>());
            return sortedEmployees;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error sorting employees by salary", e);
            throw e;
        }
    }

    /**
     * Sorts employees by performance rating (the best first)
     * @return A list of employees sorted by performance in descending order
     */
    public List<Employee<T>> sortByPerformance() {
        try {
            List<Employee<T>> sortedEmployees = new ArrayList<>(employees.values());
            sortedEmployees.sort(new EmployeePerformanceComparator<>());
            return sortedEmployees;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error sorting employees by performance", e);
            throw e;
        }
    }

    /**
     * Sorts employees by years of experience (most experienced first)
     * @return A list of employees sorted by years of experience in descending order
     */
    public List<Employee<T>> sortByExperience() {
        try {
            List<Employee<T>> sortedEmployees = new ArrayList<>(employees.values());
            Collections.sort(sortedEmployees); // Uses the natural ordering defined by compareTo
            return sortedEmployees;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error sorting employees by experience", e);
            throw e;
        }
    }

    /**
     * Gives a salary raise to employees with high performance ratings
     * @param minRating The minimum performance rating required for a raise
     * @param percentageRaise The percentage salary raise
     * @return The number of employees who received a raise
     * @throws IllegalArgumentException If minRating is negative or percentageRaise is negative
     */
    public int giveSalaryRaise(double minRating, double percentageRaise) throws IllegalArgumentException {
        try {
            if (minRating < 0) {
                throw new IllegalArgumentException("Minimum rating cannot be negative");
            }

            if (percentageRaise < 0) {
                throw new IllegalArgumentException("Percentage raise cannot be negative");
            }

            int count = 0;
            for (Employee<T> employee : employees.values()) {
                if (employee.getPerformanceRating() >= minRating) {
                    double newSalary = employee.getSalary() * (1 + percentageRaise / 100);
                    employee.setSalary(newSalary);
                    count++;
                }
            }
            logger.info("Salary raise applied to " + count + " employees");
            return count;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error giving salary raise", e);
            throw e;
        }
    }

    /**
     * Retrieves the top N highest-paid employees
     * @param n The number of top employees to retrieve
     * @return A list of the top N highest-paid employees
     * @throws IllegalArgumentException If n is not positive
     */
    public List<Employee<T>> getTopPaidEmployees(int n) throws IllegalArgumentException {
        try {
            if (n <= 0) {
                throw new IllegalArgumentException("Number of employees must be positive");
            }

            return employees.values().stream()
                    .sorted(new EmployeeSalaryComparator<>())
                    .limit(n)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error getting top paid employees", e);
            throw e;
        }
    }

    /**
     * Calculates the average salary of employees in a specific department
     * @param department The department to calculate the average salary for
     * @return The average salary, or 0 if no employees in the department
     * @throws InvalidDepartmentException If the department is null or empty
     */
    public double getAverageSalaryByDepartment(String department) throws InvalidDepartmentException {
        try {
            if (department == null || department.trim().isEmpty()) {
                throw new InvalidDepartmentException("Department cannot be null or empty");
            }

            return employees.values().stream()
                    .filter(emp -> emp.getDepartment().equalsIgnoreCase(department))
                    .mapToDouble(Employee::getSalary)
                    .average()
                    .orElse(0);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error calculating average salary by department", e);
            throw e;
        }
    }

    /**
     * Gets an employee by their ID
     * @param employeeId The ID of the employee to retrieve
     * @return The employee
     * @throws EmployeeNotFoundException If no employee with the given ID exists
     */
    public Employee<T> getEmployee(T employeeId) throws EmployeeNotFoundException {
        try {
            if (employeeId == null) {
                throw new IllegalArgumentException("Employee ID cannot be null");
            }

            Employee<T> employee = employees.get(employeeId);
            if (employee == null) {
                throw new EmployeeNotFoundException("No employee found with ID: " + employeeId);
            }
            return employee;
        } catch (EmployeeNotFoundException e) {
            logger.warning(e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error getting employee by ID", e);
            throw e;
        }
    }

    /**
     * Gets the number of employees
     * @return The number of employees
     */
    public int getEmployeeCount() {
        try {
            return employees.size();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error getting employee count", e);
            throw e;
        }
    }

    /**
     * Gets all departments
     * @return A set of all departments
     */
    public Set<String> getAllDepartments() {
        try {
            return new HashSet<>(validDepartments);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error getting all departments", e);
            throw e;
        }
    }

    /**
     * Registers a new valid department
     * @param department The department to register
     * @throws InvalidDepartmentException If the department is null or empty
     */
    public void registerDepartment(String department) throws InvalidDepartmentException {
        try {
            if (department == null || department.trim().isEmpty()) {
                throw new InvalidDepartmentException("Department cannot be null or empty");
            }
            validDepartments.add(department);
            logger.info("Department registered: " + department);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error registering department", e);
            throw e;
        }
    }

}
