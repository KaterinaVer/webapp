package com.godeltech.mastery.task.test.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.godeltech.mastery.task.dto.Employee;
import com.godeltech.mastery.task.dto.Gender;
import com.godeltech.mastery.task.rest.EmployeeController;
import com.godeltech.mastery.task.service.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeControllerTest {

    @Mock
    private EmployeeService service;

    @InjectMocks
    private EmployeeController employeeController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
    }

    @Test
    public void getAllEmployeesTest() throws Exception {
        mockMvc.perform(
                get("/employees")
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getEmployeeByIdTest() throws Exception {
        mockMvc.perform(
                get("/employees/1")
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void addEmployeeTest() throws Exception{
        String employee = new ObjectMapper().writeValueAsString(new Employee("Genry","Mitchel",
                5,"Manager", Gender.MALE, LocalDate.of(1980, 10,12)));

        mockMvc.perform(
                post("/employees")
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

        mockMvc.perform(
                put("/employees/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employee)
        ).andDo(print())
                .andExpect(status().isAccepted());
    }

    @Test
    public void deleteEmployeeTest() throws Exception {
        mockMvc.perform(
                delete("/employees/2")
                        .accept(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(status().isOk());
    }
}
