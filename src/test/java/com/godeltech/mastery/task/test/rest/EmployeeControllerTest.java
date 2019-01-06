package com.godeltech.mastery.task.test.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.godeltech.mastery.task.dto.Employee;
import com.godeltech.mastery.task.dto.Gender;
import com.godeltech.mastery.task.rest.EmployeeController;
import com.godeltech.mastery.task.service.EmployeeService;
import com.godeltech.mastery.task.test.config.AppTestConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = AppTestConfiguration.class)
@WebAppConfiguration
public class EmployeeControllerTest {

    @InjectMocks
    private EmployeeController employeeController;

    private MockMvc mockMvc;

    @Mock
    private EmployeeService service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
    }

    @Test
    public void getAllEmployeesTest() throws Exception {

        List<Employee> employees = new ArrayList<>();

        employees.add(new Employee(1L,"Genry","Mitchel", 5,
                "Manager", Gender.MALE, LocalDate.of(1980, 10,12)));
        employees.add(new Employee(2L,"Lukas","White", 5,
                "Manager", Gender.MALE, LocalDate.of(1985, 6,8)));

        when(service.getEmployees()).thenReturn(employees);

        mockMvc.perform(
                get("/employee")
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getEmployeeByIdTest() throws Exception {
        Employee employee = new Employee("Genry","Mitchel", 5,
                "Manager", Gender.MALE, LocalDate.of(1980, 10,12));

        when(service.getEmployeeById(anyLong())).thenReturn(employee);

        mockMvc.perform(
                get("/employee/1")
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void addEmployeeTest() throws Exception{
        String employee = new ObjectMapper().writeValueAsString(new Employee("Genry","Mitchel",
                5,"Manager", Gender.MALE, LocalDate.of(1980, 10,12)));

        when(service.addEmployee(any(Employee.class))).thenReturn(3L);

        mockMvc.perform(
                post("/employee")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employee)
        ).andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void updateEmployeeTest() throws Exception{
        String employee = new ObjectMapper().writeValueAsString(new Employee(1L,"Genry","Mitchel", 5,
                "Manager", Gender.MALE, LocalDate.of(1980, 10,12)));

        doNothing().when(service).updateEmployee(any(Employee.class));

        mockMvc.perform(
                put("/employee/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employee)
        ).andDo(print())
                .andExpect(status().isAccepted());
    }

    @Test
    public void deleteEmployeeTest() throws Exception {

        when(service.deleteEmployee(anyLong())).thenReturn(2L);

        mockMvc.perform(
                delete("/employee/2")
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(status().isOk());
    }
}
