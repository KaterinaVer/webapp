package com.godeltech.mastery.task.service;

import com.godeltech.mastery.task.dao.EmployeeDao;
import com.godeltech.mastery.task.dto.Employee;
import com.godeltech.mastery.task.service.exception.OperationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {


    private EmployeeDao employeeDao;

    @Autowired
    public EmployeeService(EmployeeDao employeeDao){
        this.employeeDao=employeeDao;
    }

    private void checkEmployee(Employee employee){
        if (employee == null) throw new OperationFailedException("Employee should not be null");
        if (employee.getFirstName() == null) throw new OperationFailedException("Employee's first name should not be null");
        if (employee.getLastName() == null) throw new OperationFailedException("Employee's last name should not be null");
        if (employee.getDepartmentId() == null) throw new OperationFailedException("Employee's department ID should not be null");
        if (employee.getJobTitle() == null) throw new OperationFailedException("Employee's job title should not be null");
        if (employee.getGender() == null) throw new OperationFailedException("Employee's gender should not be null");
        if (employee.getDateOfBirth() == null) throw new OperationFailedException("Employee's date of birth should not be null");
    }

    public List<Employee> getEmployees(){
        return employeeDao.findAll();
    }

    public Employee getEmployeeById(Long employeeId){
        if(employeeId<=0) throw new OperationFailedException("Employee's ID should be more than 0");
        return employeeDao.getEmployeeById(employeeId);
    }

    public Long addEmployee(Employee employee){
        checkEmployee(employee);
        return employeeDao.insertEmployee(employee);
    }

    public void updateEmployee(Employee employee){
        checkEmployee(employee);
        employeeDao.updateEmployee(employee);
    }

    public Long deleteEmployee(Long employeeId){
        if(employeeId<=0) throw new OperationFailedException("Employee's ID should be more than 0");
        return employeeDao.deleteEmployee(employeeId);
    }

}
