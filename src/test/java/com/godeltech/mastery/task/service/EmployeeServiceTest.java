package com.godeltech.mastery.task.service;

import com.godeltech.mastery.task.config.ServiceTestConfiguration;
import com.godeltech.mastery.task.dto.Employee;
import com.godeltech.mastery.task.dto.Gender;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ServiceTestConfiguration.class)
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
        Employee employeeFromDb = employeeService.getEmployeeById((long)1);
        assertEquals(employeeFromDb.getFirstName(), "Archie");
    }

    @Test
    public void deleteTest(){
        Long id= employeeService.deleteEmployee((long)1);
        List<Employee> employee = employeeService.getEmployees();
        Assert.assertTrue(employee.size() == 1);
    }

    @Test
    public void insertTest() throws ParseException {
        String format = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date dateOfBirth = simpleDateFormat.parse("1980-10-12");
        Employee employee= new Employee("Genry","Mitchel",
                5,"Manager", Gender.MALE, dateOfBirth);
        Long id = employeeService.addEmployee(employee);
        Employee employeeFromDb = employeeService.getEmployeeById(id);
        assertEquals(employeeFromDb.getFirstName(), "Genry");
    }

    @Test
    public void updateTest(){
        Employee employee = employeeService.getEmployeeById((long)1);
        employee.setFirstName("Dan");
        employeeService.updateEmployee(employee);
        employee = employeeService.getEmployeeById((long)1);
        Assert.assertTrue(employee.getDepartmentId()==23);
        assertEquals(employee.getFirstName(), "Dan");
    }
}
