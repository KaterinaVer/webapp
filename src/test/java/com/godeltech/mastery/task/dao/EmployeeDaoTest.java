package com.godeltech.mastery.task.dao;

import com.godeltech.mastery.task.dto.Employee;
import com.godeltech.mastery.task.dto.Gender;
import com.godeltech.mastery.task.config.DaoTestConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.junit.Test;
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
    public void findAllTest() {
        List<Employee> employee = employeeDao.findAll();

        assertEquals(2, employee.size());
    }

    @Test
    public void getByIdTest(){
        Employee employee = employeeDao.getEmployeeById(1L);

        assertEquals("Archie", employee.getFirstName());
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void getByNonexistentIdTest(){
        employeeDao.getEmployeeById(6L);
    }

    @Test
    public void deleteTest(){
        employeeDao.deleteEmployee(1L);
        List<Employee> employee = employeeDao.findAll();

        assertEquals(1, employee.size());
    }

    @Test
    public void insertTest() {
        Employee employee= new Employee("Genry","Mitchel", 5,
                "Manager", Gender.MALE, new GregorianCalendar(1980, 10,12).getTime());

        Long id = employeeDao.insertEmployee(employee);

        Employee employeeFromDb = employeeDao.getEmployeeById(id);

        assertEquals(employee.getFirstName(), employeeFromDb.getFirstName());
    }

    @Test
    public void updateTest() {
        Employee employee = employeeDao.getEmployeeById(1L);
        employee.setFirstName("Test");

        employeeDao.updateEmployee(employee);

        employee = employeeDao.getEmployeeById(1L);

        assertEquals(Integer.valueOf(23), employee.getDepartmentId());
        assertEquals("Test", employee.getFirstName());
    }
}