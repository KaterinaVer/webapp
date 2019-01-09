package com.godeltech.mastery.task.service;

import com.godeltech.mastery.task.dao.EmployeeDao;
import com.godeltech.mastery.task.dto.Employee;
import com.godeltech.mastery.task.service.exception.OperationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeDao employeeDao;

    @Autowired
    public EmployeeServiceImpl(EmployeeDao employeeDao){
        this.employeeDao=employeeDao;
    }

    private void checkEmployee(Employee employee){
        if (employee == null) {
            throw new OperationFailedException("Employee should not be null");
        }
        if (employee.getFirstName() == null) {
            throw new OperationFailedException("Employee's first name should not be null");
        }
        if (employee.getLastName() == null) {
            throw new OperationFailedException("Employee's last name should not be null");
        }
        if (employee.getDepartmentId() == null) {
            throw new OperationFailedException("Employee's department ID should not be null");
        }
        if (employee.getJobTitle() == null) {
            throw new OperationFailedException("Employee's job title should not be null");
        }
        if (employee.getGender() == null) {
            throw new OperationFailedException("Employee's gender should not be null");
        }
        if (employee.getDateOfBirth() == null) {
            throw new OperationFailedException("Employee's date of birth should not be null");
        }

        if ( getAge(employee) < 16) {
            throw new OperationFailedException("Employee should be older");
        }
    }

    private static int getAge(Employee employee){
        Calendar dateOfBirth = Calendar.getInstance();
        Calendar todayDate = Calendar.getInstance();

        dateOfBirth.setTime(employee.getDateOfBirth());
        todayDate.setTime(new Date());

        return todayDate.get(Calendar.YEAR) - dateOfBirth.get(Calendar.YEAR);
    }

    @Override
    public List<Employee> getEmployees(){
        return employeeDao.findAll();
    }

    @Override
    public Employee getEmployeeById(Long employeeId){
        if(employeeId == null || employeeId <= 0){
            throw new OperationFailedException("Employee's ID should be more than 0 or not a null");
        }
        return employeeDao.getEmployeeById(employeeId);
    }

    @Override
    public Long addEmployee(Employee employee)  {
        checkEmployee(employee);

        return employeeDao.insertEmployee(employee);
    }

    @Override
    public void updateEmployee(Employee employee){
        checkEmployee(employee);
        employeeDao.updateEmployee(employee);
    }

    @Override
    public Long deleteEmployee(Long employeeId){
        if(employeeId == null || employeeId <= 0){
            throw new OperationFailedException("Employee's ID should be more than 0 or not a null");
        }
        return employeeDao.deleteEmployee(employeeId);
    }

}
