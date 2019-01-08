package com.godeltech.mastery.task.test.service;

import com.godeltech.mastery.task.test.config.ServiceIntegrationTestConfiguration;
import com.godeltech.mastery.task.dto.Employee;
import com.godeltech.mastery.task.dto.Gender;
import com.godeltech.mastery.task.service.EmployeeService;
import com.godeltech.mastery.task.service.exception.OperationFailedException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.GregorianCalendar;
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

        assertEquals(2, employee.size());
    }

    @Test
    public void getByIdTest(){
        Employee employeeFromDb = employeeService.getEmployeeById(1L);

        assertEquals("Archie", employeeFromDb.getFirstName());
    }

    @Test(expected = OperationFailedException.class)
    public void getEmployeeByIllegalIdTest() {
        employeeService.getEmployeeById(-1L);
    }

    @Test
    public void deleteTest(){
        Long id= employeeService.deleteEmployee(1L);
        List<Employee> employee = employeeService.getEmployees();

        assertEquals(1, employee.size());
    }

    @Test
    public void insertTest(){
        Employee employee= new Employee("Genry","Mitchel", 5,
                "Manager", Gender.MALE, new GregorianCalendar(1980, 10,12).getTime());

        Long id = employeeService.addEmployee(employee);

        Employee employeeFromDb = employeeService.getEmployeeById(id);

        assertEquals(employee.getDateOfBirth(), employeeFromDb.getDateOfBirth());
    }

    @Test(expected = OperationFailedException.class)
    public void addIllegalEmployeeTest() {
        Employee employee= new Employee("Genry","Mitchel", 5,
                "Manager", Gender.MALE, new GregorianCalendar(2010, 10,12).getTime());

        employeeService.addEmployee(employee);
    }

    @Test
    public void updateTest(){
        Employee employee = employeeService.getEmployeeById(1L);
        employee.setFirstName("Test");

        employeeService.updateEmployee(employee);

        employee = employeeService.getEmployeeById(1L);

        assertEquals(Integer.valueOf(23), employee.getDepartmentId());
        assertEquals("Test", employee.getFirstName());
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void getByNonexistentIdTest(){
        employeeService.getEmployeeById(5L);
    }

    @Test(expected = OperationFailedException.class)
    public void getByNullIdTest(){
        employeeService.getEmployeeById(null);
    }

    @Test(expected = OperationFailedException.class)
    public void deleteByIllegalIdTest(){
        employeeService.deleteEmployee(-1L);
    }

}
