Create schema employee;
CREATE TABLE employee_info (
                               employee_id INT AUTO_INCREMENT PRIMARY KEY,
                               first_name VARCHAR(20),
                               last_name VARCHAR(25) NOT NULL,
                               email VARCHAR(100) NOT NULL,
                               phone_number VARCHAR(20),
                               job_title CHAR(100) NOT NULL,
                               AnnualSalary DECIMAL(8, 2) NOT NULL,
                               vacation INT NOT NULL
);

