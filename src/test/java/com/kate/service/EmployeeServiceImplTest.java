package com.kate.service;

import com.kate.config.TestConfiguration;
import com.kate.dto.Employee;
import com.kate.dto.Gender;
import com.kate.service.exception.OperationFailedException;
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
@ContextConfiguration(classes = TestConfiguration.class)
@Transactional
public class EmployeeServiceImplTest {

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
        Integer num= employeeService.deleteEmployee(1L);

        assertEquals(Integer.valueOf(1), num);
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

        Employee employeeFromDb = employeeService.getEmployeeById(1L);

        assertEquals(employee.getDepartmentId(), employeeFromDb.getDepartmentId());
        assertEquals("Test", employeeFromDb.getFirstName());
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
