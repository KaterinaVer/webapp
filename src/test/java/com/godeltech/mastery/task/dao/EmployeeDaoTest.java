package com.godeltech.mastery.task.dao;

import com.godeltech.mastery.task.config.DaoTestConfiguration;
import com.godeltech.mastery.task.dto.Employee;
import com.godeltech.mastery.task.dto.Gender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;

import java.time.LocalDate;
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
        Employee employeeFromDb = employeeDao.getEmployeeById(1L);
        assertEquals(employeeFromDb.getFirstName(), "Archie");
    }

    @Test
    public void deleteTest(){
        Long id= employeeDao.deleteEmployee(1L);
        List<Employee> employee = employeeDao.findAll();
        Assert.assertTrue(employee.size() == 1);
    }

    @Test
    public void insertTest() {
        Employee employee= new Employee("Genry","Mitchel",
                5,"Manager", Gender.MALE, LocalDate.of(1980, 10,12));
        Long id = employeeDao.insertEmployee(employee);
        Employee employeeFromDb = employeeDao.getEmployeeById(id);
        assertEquals(employeeFromDb.getFirstName(), "Genry");
    }

    @Test(expected = RuntimeException.class)
    public void insertNonexistentEmployeeTest() {
        Employee employee= new Employee();
        Long id = employeeDao.insertEmployee(employee);
    }

    @Test
    public void updateTest() {
        Employee employee = employeeDao.getEmployeeById(1L);
        employee.setFirstName("Dan");
        employeeDao.updateEmployee(employee);
        employee = employeeDao.getEmployeeById(1L);
        Assert.assertTrue(employee.getDepartmentId()==23);
        assertEquals(employee.getFirstName(), "Dan");
    }
}