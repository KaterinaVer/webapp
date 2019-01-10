package com.godeltech.mastery.task.service;

import com.godeltech.mastery.task.dto.Employee;

import java.util.List;

public interface EmployeeService {

    /**
     * Get all employees.
     *
     * @return List of employees.
     */
    List<Employee> getEmployees();

    /**
     * Get employee by ID.
     *
     * @param employeeId
     * @return Employee.
     */
    Employee getEmployeeById(Long employeeId);

    /**
     * Add new employee to DB.
     *
     * @param employee
     * @return Employee ID.
     */
    Long addEmployee(Employee employee);

    /**
     * Update employee.
     *
     * @param employee
     * @return void.
     */
    void updateEmployee(Employee employee);

    /**
     * Delete employee from DB.
     *
     * @param employeeId
     * @return Count of deleted employees.
     */
    Long deleteEmployee(Long employeeId);
}