package com.godeltech.mastery.task.service;

import com.godeltech.mastery.task.config.ServiceIntegrationTestConfiguration;
import com.godeltech.mastery.task.dto.Employee;
import com.godeltech.mastery.task.dto.Gender;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ServiceIntegrationTestConfiguration.class)
@Transactional
public class EmployeeServiceTest {

    @Autowired
    EmployeeService employeeService;

    @Test
    public void getEmployeesTest(){
        List<Employee> employee = employeeService.getEmployees();
        Assert.assertTrue(employee.size() == 2);
    }

    @Test
    public void getByIdTest(){
        Employee employeeFromDb = employeeService.getEmployeeById(1L);
        assertEquals(employeeFromDb.getFirstName(), "Archie");
    }

    @Test
    public void deleteTest(){
        Long id= employeeService.deleteEmployee(1L);
        List<Employee> employee = employeeService.getEmployees();
        Assert.assertTrue(employee.size() == 1);
    }

    @Test
    public void insertTest(){
        Employee employee= new Employee("Genry","Mitchel",
                5,"Manager", Gender.MALE, LocalDate.of(1980, 10,12));
        Long id = employeeService.addEmployee(employee);
        Employee employeeFromDb = employeeService.getEmployeeById(id);
        assertEquals(employeeFromDb.getFirstName(), "Genry");
    }

    @Test
    public void updateTest(){
        Employee employee = employeeService.getEmployeeById(1L);
        employee.setFirstName("Dan");
        employeeService.updateEmployee(employee);
        employee = employeeService.getEmployeeById(1L);
        Assert.assertTrue(employee.getDepartmentId()==23);
        assertEquals(employee.getFirstName(), "Dan");
    }
}
