package com.godeltech.mastery.task.rest;

import com.godeltech.mastery.task.dto.Employee;
import com.godeltech.mastery.task.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

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
    @ResponseBody
    public List<Employee> getAllEmployees() {
        return employeeService.getEmployees();
    }

    @GetMapping("/employees/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Employee getEmployee(@PathVariable("id") Long id) {
        return employeeService.getEmployeeById(id);
    }

    @PostMapping("/employees")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ResponseEntity<Void> createEmployee(@RequestBody Employee employee, UriComponentsBuilder ucBuilder) {
        employeeService.addEmployee(employee);

        UriComponents uriComponent = ucBuilder.path("/employees/{id}").buildAndExpand(employee.getEmployeeId());
        return ResponseEntity.created(uriComponent.toUri()).build();
    }

    @PutMapping("/employees/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ResponseBody
    public void updateEmployee(@PathVariable("id") Long id, @RequestBody  Employee employee) {
        employee.setEmployeeId(id);

        employeeService.updateEmployee(employee);
    }

    @DeleteMapping("/employees/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void deleteEmployee(@PathVariable("id") long id) {
        employeeService.deleteEmployee(id);
    }

}
