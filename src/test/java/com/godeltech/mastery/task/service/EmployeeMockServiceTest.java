package com.godeltech.mastery.task.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import com.godeltech.mastery.task.config.ServiceIntegrationTestConfiguration;
import com.godeltech.mastery.task.dao.EmployeeDao;
import com.godeltech.mastery.task.dto.Employee;
import com.godeltech.mastery.task.dto.Gender;
import com.godeltech.mastery.task.service.exception.OperationFailedException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ServiceIntegrationTestConfiguration.class)
public class EmployeeMockServiceTest {

    @Mock
    private EmployeeDao daoMock;

    @InjectMocks
    private EmployeeService service;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = OperationFailedException.class)
    public void getEmployeeByIdTest() {
        when(daoMock.getEmployeeById((long)-1)).thenThrow(new OperationFailedException("Illegal Employee's ID"));
        service.getEmployeeById((long)-1);
    }

    @Test
    public void addEmployeeTest() {
        Employee employee= new Employee("Genry","Mitchel",
                5,"Manager", Gender.MALE, LocalDate.of(1980, 10,12));
        when(daoMock.insertEmployee(employee)).thenReturn((long)3);
        Long id = service.addEmployee(employee);
        assertEquals(id, Long.valueOf(3));
    }

    @Test(expected = OperationFailedException.class)
    public void updateNonexistentEmployeeTest() {
        Employee employee= new Employee((long)5,"Genry","Mitchel",
                5,"Manager", Gender.MALE, LocalDate.of(1980, 10,12));
        doThrow(new OperationFailedException("Illegal Employee's ID")).when(daoMock).updateEmployee(employee);
        service.updateEmployee(employee);
    }

    @Test(expected = OperationFailedException.class)
    public void getNonexistentEmployeesTest(){
        doThrow(new OperationFailedException("Employees don't exist")).when(daoMock).findAll();
        service.getEmployees();
    }

    @Test(expected = OperationFailedException.class)
    public void deleteNonexistentEmployeeTest() {
        when(daoMock.deleteEmployee((long)-1)).thenThrow(new OperationFailedException("Illegal Employee's ID"));
        service.deleteEmployee((long)-1);
    }

    @Test
    public void deleteEmployeeTest() {
        when(daoMock.deleteEmployee(anyLong())).thenReturn((long)2);
        Long id = service.deleteEmployee((long)2);
        assertEquals(id, Long.valueOf(2));
    }
}
