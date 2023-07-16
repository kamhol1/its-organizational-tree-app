DROP TABLE IF EXISTS employees;

CREATE TYPE position_enum AS ENUM ('PREZES', 'DYREKTOR', 'KOORDYNATOR', 'KIEROWNIK', 'PRACOWNIK');

CREATE TABLE employees (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    position position_enum NOT NULL,
    active BOOLEAN NOT NULL,
    created_on TIMESTAMP DEFAULT current_timestamp,
    updated_on TIMESTAMP DEFAULT current_timestamp
);