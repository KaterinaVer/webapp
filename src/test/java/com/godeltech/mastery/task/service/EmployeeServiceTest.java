package com.godeltech.mastery.task.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import com.godeltech.mastery.task.config.ServiceTestConfiguration;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.GregorianCalendar;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ServiceTestConfiguration.class)
public class EmployeeServiceTest {

    @Autowired
    @Mock
    private EmployeeDao daoMock;

    @Autowired
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
                5,"Manager", Gender.MALE, new GregorianCalendar(1980, 10-1, 12).getTime());
        when(daoMock.insertEmployee(employee)).thenReturn((long)3);
        Long id = service.addEmployee(employee);
        assertEquals(id, Long.valueOf(3));
    }

    /*@Test(expected = OperationFailedException.class)
    public void updateEmployeeTest() {
        Employee employee= new Employee((long)5,"Genry","Mitchel",
                5,"Manager", Gender.MALE, new GregorianCalendar(1980, 10-1, 12).getTime());
        doNothing().when(daoMock.updateEmployee(employee)).thenThrow(new OperationFailedException("Illegal Employee's ID"));
        Long id = service.addEmployee(employee);
        assertEquals(id, Long.valueOf(3));
    }*/

}
