CREATE TABLE workflow
(
  id INT PRIMARY KEY,
  prev_id INT REFERENCES workflow(id),
  name VARCHAR(256) NOT NULL,
  description TEXT
);
CREATE INDEX workflow_prev_id_index ON workflow (prev_id);

CREATE TABLE users
(
  id SERIAL PRIMARY KEY,
  hhid VARCHAR(128) NOT NULL,
  first_name VARCHAR(128)                         NOT NULL,
  last_name VARCHAR(128)                          NOT NULL,
  middle_name VARCHAR(128)
);

CREATE TABLE employee
(
  id SERIAL PRIMARY KEY,
  first_name VARCHAR(128)                         NOT NULL,
  last_name VARCHAR(128)                          NOT NULL,
  middle_name VARCHAR(128),
  position VARCHAR(256)                           NOT NULL,
  email VARCHAR(128)                              NOT NULL,
  mobile_phone BIGINT,
  internal_phone INT,
  gender VARCHAR(6)                              NOT NULL,
  employment_timestamp TIMESTAMP DEFAULT now() NOT NULL,
  update_timestamp TIMESTAMP,
  curator_id INT REFERENCES users (id) NOT NULL,
  workflow_id INT REFERENCES workflow(id) NOT NULL
);
CREATE INDEX employee_curator_id_index ON employee (curator_id);
CREATE INDEX employee_workflow_id_index ON employee (workflow_id);

-- Some data for simplify testing of user cases
INSERT INTO workflow (id, name) VALUES (1, 'Начальный шаг');
INSERT INTO users (hhid, first_name, last_name) VALUES ('example_hhid', 'Билл', 'Гейтс');
INSERT INTO employee (first_name, last_name, position, email, gender, employment_timestamp, curator_id, workflow_id)
    VALUES ('Джон', 'МакКлейн', 'Охранник', 'die@hard.com', 'MALE', now(), 1, 1);