package com.godeltech.mastery.task.rest;

import com.godeltech.mastery.task.dto.Employee;
import com.godeltech.mastery.task.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getAllEmployees() {
        return employeeService.getEmployees();
    }

    @GetMapping("/employees/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Employee getEmployee(@PathVariable("id") Long id) {
        return employeeService.getEmployeeById(id);
    }

    @PostMapping("/employees")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createEmployee(@Valid @RequestBody Employee employee, UriComponentsBuilder ucBuilder) {
        Long id = employeeService.addEmployee(employee);

        UriComponents uriComponent = ucBuilder.path("/employees/{id}").buildAndExpand(id);
        return ResponseEntity.created(uriComponent.toUri()).build();
    }

    @PutMapping("/employees/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateEmployee(@PathVariable("id") Long id, @Valid @RequestBody  Employee employee) {
        employee.setEmployeeId(id);

        employeeService.updateEmployee(employee);
    }

    @DeleteMapping("/employees/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteEmployee(@PathVariable("id") Long id) {
        employeeService.deleteEmployee(id);
    }

}
