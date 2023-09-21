Create schema employee;
CREATE TABLE employee_info (
                               employee_id INT AUTO_INCREMENT PRIMARY KEY,
                               name VARCHAR(20) NOT NULL,
                               email VARCHAR(100) NOT NULL,
                               phone_number VARCHAR(20),
                               job_title CHAR(100) NOT NULL,
                               AnnualSalary DECIMAL(8, 2) NOT NULL
);

CREATE TABLE user_credential (
                               username VARCHAR(20) NOT NULL,
                               password VARCHAR(25) NOT NULL,
                               user_id VARCHAR(100) NOT NULL
);
CREATE TABLE vacation_request (
                               employee_id INT AUTO_INCREMENT PRIMARY KEY,
                               start_date VARCHAR(20) NOT NULL,
                               end_date VARCHAR(25) NOT NULL,
                               reason VARCHAR(300),
                               status VARCHAR(20),
                               FOREIGN KEY (employee_id) references employee_info(employee_id) ON DELETE CASCADE ON UPDATE CASCADE
);

