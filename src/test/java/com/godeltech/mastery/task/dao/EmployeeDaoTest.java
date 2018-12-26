package com.godeltech.mastery.task.dao;


import com.godeltech.mastery.task.dto.Employee;

import com.godeltech.mastery.task.dto.Gender;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import org.junit.Test;


import java.util.Date;
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
    public void deleteTest(){

    Integer id= employeeDao.deleteEmployee((long)1);

    List<Employee> employee = employeeDao.findAll();
    Assert.assertTrue(employee.size() == 1);

    }

    @Test
    public void insertTest(){
        Employee employee= new Employee("Genry","Mitchel",
                5,"Manager", Gender.male, new GregorianCalendar(1980, 10-1, 12).getTime());
        Long id = employeeDao.insertEmployee(employee);
        System.out.println(id);
        System.out.println(employee.toString());
        Employee employeeFromDb = employeeDao.getEmployeeById(id);
        System.out.println(employeeFromDb.toString());
    }
}