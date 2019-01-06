package com.godeltech.mastery.task.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.time.LocalDate;

public class Employee {

    private Long employeeId;
    private String firstName;
    private String lastName;
    private Integer departmentId;
    private String jobTitle;
    private Gender gender;
    @JsonSerialize(using = ToStringSerializer.class)
    private LocalDate dateOfBirth;

    public Employee() {
    }

    public Employee(String firstName, String lastName, Integer departmentId, String jobTitle, Gender gender, LocalDate dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.departmentId = departmentId;
        this.jobTitle = jobTitle;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
    }

    public Employee(Long employeeId, String firstName, String lastName, Integer departmentId, String jobTitle, Gender gender, LocalDate dateOfBirth) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.departmentId = departmentId;
        this.jobTitle = jobTitle;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String toString() {
        return "Employee [ id =" + this.employeeId + ", first name=" + this.firstName
                + ", last name=" + this.lastName + ", department id =" + this.departmentId
                + ", job title =" + this.jobTitle +", gender =" + this.gender + ", date of birth ="
                + this.dateOfBirth + " ]";
    }

}
