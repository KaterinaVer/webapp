DROP TABLE IF EXISTS employee;
CREATE TABLE employee (
  employee_id SERIAL primary key,
  first_name VARCHAR(50) NOT NULL,
  last_name VARCHAR(50) NOT NULL,
  department_id INTEGER NOT NULL,
  job_title VARCHAR(50) NOT NULL,
  gender gender NOT NULL,
  date_of_birth date
  );