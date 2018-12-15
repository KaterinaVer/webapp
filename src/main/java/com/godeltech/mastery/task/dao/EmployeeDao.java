package com.godeltech.mastery.task.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
//import org.springframework.stereotype.Component;
import com.godeltech.mastery.task.dto.Employee;
import com.godeltech.mastery.task.dto.Gender;

public class EmployeeDao {

    public static final String TABLE_NAME = "employee";

    private static final String EMPLOYEE_ID = "employeeId";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String DEPARTMENT_ID = "departmentId";
    private static final String JOB_TITLE = "jobTitle";
    private static final String GENDER = "gender";
    private static final String DATE_OF_BIRTH = "dateOfBirth";

    public static final String SQL_FIND_ALL = "SELECT * FROM " + TABLE_NAME;
    public static final String SQL_INSERT = "INSERT INTO " + TABLE_NAME +
            " (" + FIRST_NAME + ", " + LAST_NAME + "," + DEPARTMENT_ID + "," + JOB_TITLE + "," + GENDER + "," + DATE_OF_BIRTH + ") "
            + "VALUES (?, ?, ?, ?, ?::gender, ?)";
    public static final String SQL_UPDATE = "UPDATE " + TABLE_NAME
            + " SET " + FIRST_NAME + " = ?" + LAST_NAME + " = ?" + DEPARTMENT_ID + " = ?" + JOB_TITLE + " = ?" + GENDER + " = ?" + DATE_OF_BIRTH + " = ?"
            + " WHERE " + EMPLOYEE_ID + " = ?";
    public static final String SQL_DELETE = "DELETE FROM " + TABLE_NAME + " WHERE " + EMPLOYEE_ID + " = ?";


    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<Employee> findAll() {
        return namedParameterJdbcTemplate.query(SQL_FIND_ALL, new RowMapper<Employee>() {
            public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
                Employee employee = new Employee();
                employee.setEmployeeId(rs.getLong(EMPLOYEE_ID));
                employee.setFirstName(rs.getString(FIRST_NAME));
                employee.setLastName(rs.getString(LAST_NAME));
                employee.setDepartmentId(rs.getInt(DEPARTMENT_ID));
                employee.setJobTitle(rs.getString(JOB_TITLE));
                employee.setGender(Gender.valueOf(rs.getString(GENDER)));
                employee.setDateOfBirth(rs.getString(DATE_OF_BIRTH));
                return employee;
            }
        });
    }
    public Long insertEmployee(Employee employee) throws DataAccessException {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();

        namedParameters.addValue(FIRST_NAME, employee.getFirstName());
        namedParameters.addValue(LAST_NAME, employee.getLastName());
        namedParameters.addValue(DEPARTMENT_ID, employee.getDepartmentId());
        namedParameters.addValue(JOB_TITLE, employee.getJobTitle());
        namedParameters.addValue(GENDER, employee.getGender());
        namedParameters.addValue(DATE_OF_BIRTH, employee.getDateOfBirth());

        namedParameterJdbcTemplate.update(SQL_INSERT, namedParameters, keyHolder);

        return keyHolder.getKey().longValue();
    }


    public void updateEmployee(Employee employee) throws DataAccessException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(EMPLOYEE_ID, employee.getEmployeeId());
        params.put(FIRST_NAME, employee.getFirstName());
        params.put(LAST_NAME, employee.getLastName());
        params.put(DEPARTMENT_ID, employee.getDepartmentId());
        params.put(JOB_TITLE, employee.getJobTitle());
        params.put(GENDER, employee.getGender());
        params.put(DATE_OF_BIRTH, employee.getDateOfBirth());

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameterJdbcTemplate.update(SQL_UPDATE, namedParameters);
    }

    public Integer deleteEmployee(Long employeeId) throws DataAccessException {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource(EMPLOYEE_ID,employeeId);
        return namedParameterJdbcTemplate.update(SQL_DELETE, namedParameters);
    }


}
