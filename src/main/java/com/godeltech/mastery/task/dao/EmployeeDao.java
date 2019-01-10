package com.godeltech.mastery.task.dao;

import com.godeltech.mastery.task.dto.Employee;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * DAO interface for Employee.
 */
public interface EmployeeDao {

    /**
     * Get all employees.
     *
     * @return List of employees.
     * @throws DataAccessException
     */
    List<Employee> findAll() throws DataAccessException;

    /**
     * Get employee by ID.
     *
     * @param employeeId
     * @return Employee.
     * @throws DataAccessException
     */
    Employee getEmployeeById(Long employeeId) throws DataAccessException;

    /**
     * Add new employee to DB.
     *
     * @param employee
     * @return Employee ID.
     * @throws DataAccessException
     */
    Long insertEmployee(Employee employee) throws DataAccessException;

    /**
     * Update employee.
     *
     * @param employee
     * @return void.
     * @throws DataAccessException
     */
    void updateEmployee(Employee employee) throws DataAccessException;

    /**
     * Delete employee from DB.
     *
     * @param employeeId
     * @return Count of deleted employees.
     * @throws DataAccessException
     */
    Integer deleteEmployee(Long employeeId) throws DataAccessException;
}
