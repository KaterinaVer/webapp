package com.godeltech.mastery.task.dao;

import com.godeltech.mastery.task.config.DaoTestConfiguration;
import com.godeltech.mastery.task.dto.Employee;
import com.godeltech.mastery.task.dto.Gender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;

import java.util.GregorianCalendar;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DaoTestConfiguration.class)
@Transactional

public class EmployeeDaoTest {

    @Autowired
    EmployeeDao employeeDao;

    @Test
    public void findAllTest() throws Exception {

        List<Employee> employee = employeeDao.findAll();
        Assert.assertTrue(employee.size() == 2);

    }
    @Test
    public void getByIdTest(){
        Employee employeeFromDb = employeeDao.getEmployeeById((long)1);
        assertEquals(employeeFromDb.getFirstName(), "Archie");
    }

    @Test
    public void deleteTest(){

    Integer id= employeeDao.deleteEmployee((long)1);

    List<Employee> employee = employeeDao.findAll();
    Assert.assertTrue(employee.size() == 1);

    }

    @Test
    public void insertTest(){
        Employee employee= new Employee("Genry","Mitchel",
                5,"Manager", Gender.MALE, new GregorianCalendar(1980, 10-1, 12).getTime());
        Long id = employeeDao.insertEmployee(employee);
        Employee employeeFromDb = employeeDao.getEmployeeById(id);
        assertEquals(employeeFromDb.getFirstName(), "Genry");
    }

    @Test
    public void updateTest(){
        Employee employee = employeeDao.getEmployeeById((long)1);
        employee.setFirstName("Dan");
        employeeDao.updateEmployee(employee);
        employee = employeeDao.getEmployeeById((long)1);
        Assert.assertTrue(employee.getDepartmentId()==23);
        assertEquals(employee.getFirstName(), "Dan");
    }
}