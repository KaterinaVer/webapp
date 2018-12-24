package com.godeltech.mastery.task.dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.context.annotation.PropertySource;
import org.springframework.core.NestedRuntimeException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import com.godeltech.mastery.task.dto.Employee;
import com.godeltech.mastery.task.dto.Gender;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class EmployeeDao {

    //public static final String TABLE_NAME = "employee";

    private static final String EMPLOYEE_ID = "employeeId";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String DEPARTMENT_ID = "departmentId";
    private static final String JOB_TITLE = "jobTitle";
    private static final String GENDER = "gender";
    private static final String DATE_OF_BIRTH = "dateOfBirth";

    @Value("${EmployeeDaoSql.findAll}")
    private String findAllSql;
    @Value("${EmployeeDaoSql.getEmployeeById}")
    private String getEmployeeByIdSql;
    @Value("${EmployeeDaoSql.addEmployee}")
    private String addEmployeeSql;
    @Value("${EmployeeDaoSql.updateEmployee}")
    private String updateEmployeeSql;
    @Value("${EmployeeDaoSql.deleteEmployee}")
    private String deleteEmployeeSql;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public EmployeeDao(DataSource dataSource) {
       namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    private RowMapper<Employee> employeeRowMapper =(resultSet,i)->{
        return new Employee(
                resultSet.getLong(EMPLOYEE_ID),
                resultSet.getString(FIRST_NAME),
                resultSet.getString(LAST_NAME),
                resultSet.getInt(DEPARTMENT_ID),
                resultSet.getString(JOB_TITLE),
                Gender.valueOf(resultSet.getString(GENDER)),
                resultSet.getDate(DATE_OF_BIRTH)
                );
    };

    public List<Employee> findAll() throws DataAccessException{
        return namedParameterJdbcTemplate.query(findAllSql, employeeRowMapper);

    }

   public Employee getEmployeeById(Long employeeId) throws DataAccessException {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource(EMPLOYEE_ID, employeeId);

        return namedParameterJdbcTemplate.queryForObject(getEmployeeByIdSql, namedParameters, employeeRowMapper);
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

        namedParameterJdbcTemplate.update(addEmployeeSql, namedParameters, keyHolder);

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
        namedParameterJdbcTemplate.update(updateEmployeeSql, namedParameters);
    }

    public Integer deleteEmployee(Long employeeId) throws DataAccessException {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource(EMPLOYEE_ID,employeeId);
        return namedParameterJdbcTemplate.update(deleteEmployeeSql, namedParameters);
    }


}
