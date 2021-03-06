package com.kate.dao;

import java.sql.Types;
import java.util.List;

import com.kate.dto.Employee;
import com.kate.dto.Gender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class EmployeeDaoImpl implements EmployeeDao {

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
    public EmployeeDaoImpl(DataSource dataSource) {
       namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    private RowMapper<Employee> employeeRowMapper =(resultSet, i)->new Employee(
                resultSet.getLong("employee_id"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getInt("department_id"),
                resultSet.getString("job_title"),
                Gender.valueOf(resultSet.getString("gender")),
                resultSet.getDate("date_of_birth")
                );


    @Override
    public List<Employee> findAll() throws DataAccessException{
        return namedParameterJdbcTemplate.query(findAllSql, employeeRowMapper);

    }

    @Override
    public Employee getEmployeeById(Long employeeId) throws DataAccessException {
        return namedParameterJdbcTemplate
                .queryForObject(getEmployeeByIdSql, new MapSqlParameterSource(EMPLOYEE_ID, employeeId), employeeRowMapper);
    }

    @Override
    public Long insertEmployee(Employee employee) throws DataAccessException {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();

        namedParameters.registerSqlType("gender", Types.OTHER);
        namedParameters.addValue(FIRST_NAME, employee.getFirstName());
        namedParameters.addValue(LAST_NAME, employee.getLastName());
        namedParameters.addValue(DEPARTMENT_ID, employee.getDepartmentId());
        namedParameters.addValue(JOB_TITLE, employee.getJobTitle());
        namedParameters.addValue(GENDER, employee.getGender());
        namedParameters.addValue(DATE_OF_BIRTH, employee.getDateOfBirth());

        namedParameterJdbcTemplate
                .update(addEmployeeSql, namedParameters, keyHolder,new String[]{"employee_id"});

        return keyHolder.getKey().longValue();
    }

    @Override
    public void updateEmployee(Employee employee) throws DataAccessException {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();

        namedParameters.registerSqlType("gender", Types.OTHER);
        namedParameters.addValue(EMPLOYEE_ID, employee.getEmployeeId());
        namedParameters.addValue(FIRST_NAME, employee.getFirstName());
        namedParameters.addValue(LAST_NAME, employee.getLastName());
        namedParameters.addValue(DEPARTMENT_ID, employee.getDepartmentId());
        namedParameters.addValue(JOB_TITLE, employee.getJobTitle());
        namedParameters.addValue(GENDER, employee.getGender());
        namedParameters.addValue(DATE_OF_BIRTH, employee.getDateOfBirth());

        namedParameterJdbcTemplate.update(updateEmployeeSql, namedParameters);
    }

    @Override
    public Integer deleteEmployee(Long employeeId) throws DataAccessException {
        return namedParameterJdbcTemplate
                .update(deleteEmployeeSql, new MapSqlParameterSource(EMPLOYEE_ID,employeeId));
    }
}
