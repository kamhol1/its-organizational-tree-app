DROP TABLE IF EXISTS departments;

CREATE TABLE departments (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    short_name VARCHAR(10) NOT NULL,
    division VARCHAR(10) NOT NULL,
    manager_id INT REFERENCES employees(id) NOT NULL,
    parent_department_id INT REFERENCES departments(id),
    created_on TIMESTAMP DEFAULT current_timestamp,
    updated_on TIMESTAMP DEFAULT current_timestamp
);

ALTER TABLE employees ADD COLUMN department_id INT REFERENCES departments(id);