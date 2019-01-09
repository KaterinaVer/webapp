package com.godeltech.mastery.task.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import com.godeltech.mastery.task.dao.EmployeeDao;
import com.godeltech.mastery.task.dto.Employee;
import com.godeltech.mastery.task.dto.Gender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeMockServiceTest {

    @Mock
    private EmployeeDao daoMock;

    @InjectMocks
    private EmployeeServiceImpl service;

    @Test
    public void getEmployeeByIdTest() {
        Employee employee = new Employee("Genry","Mitchel", 5,
                "Manager", Gender.MALE, new GregorianCalendar(1980, 10,12).getTime());

        when(daoMock.getEmployeeById(anyLong())).thenReturn(employee);

        Employee employeeFromDB = service.getEmployeeById(3L);

        assertEquals(employee, employeeFromDB);
    }

    @Test
    public void addEmployeeTest() {
        Employee employee= new Employee("Genry","Mitchel",
                5,"Manager", Gender.MALE, new GregorianCalendar(1980, 10,12).getTime());
        when(daoMock.insertEmployee(employee)).thenReturn(3L);

        Long id = service.addEmployee(employee);

        assertEquals(Long.valueOf(3), id);
    }

    @Test
    public void getEmployeesTest(){

        List<Employee> employees = new ArrayList<>();

        employees.add(new Employee(1L,"Genry","Mitchel", 5,
                "Manager", Gender.MALE, new GregorianCalendar(1980, 10,12).getTime()));

        when(daoMock.findAll()).thenReturn(employees);

        employees = service.getEmployees();

        assertEquals(1, employees.size());
    }

    @Test
    public void updateEmployeeTest() {
        Employee employee= new Employee( 2L,"Genry","Mitchel", 5,
                "Manager", Gender.MALE, new GregorianCalendar(1980, 10,12).getTime());

        doNothing().when(daoMock).updateEmployee(employee);

        service.updateEmployee(employee);

        verify(daoMock).updateEmployee(employee);
    }

    @Test
    public void deleteEmployeeTest() {
        when(daoMock.deleteEmployee(anyLong())).thenReturn(2L);

        Long id = service.deleteEmployee(2L);

        assertEquals(Long.valueOf(2), id);
    }
}
