CREATE TABLE employee
(
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(128) NOT NULL,
    last_name VARCHAR(128) NOT NULL,
    middle_name VARCHAR(128),
    position VARCHAR(256) NOT NULL,
    email VARCHAR(128) NOT NULL,
    employment_timestamp TIMESTAMP DEFAULT now() NOT NULL,
    update_timestamp TIMESTAMP
);