CREATE TABLE workflow_step
(
  id INT PRIMARY KEY,
  name VARCHAR(256) NOT NULL,
  description TEXT
);

CREATE TABLE workflow
(
  id SERIAL PRIMARY KEY,
  name VARCHAR(128)
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
  workflow_step_id INT REFERENCES workflow_step(id) NOT NULL,
  workflow_id INT REFERENCES workflow(id) NOT NULL
);

CREATE TABLE transition
(
  id SERIAL PRIMARY KEY,
  workflow_id INT REFERENCES workflow(id) NOT NULL,
  workflow_step_id INT REFERENCES workflow_step(id) NOT NULL,
  workflow_step_next_id INT REFERENCES workflow_step(id)
);

INSERT INTO workflow_step (id, name) VALUES (1, 'first point');
INSERT INTO workflow_step (id, name) VALUES (2, 'second point');
INSERT INTO workflow VALUES (default, 'all');
INSERT INTO employee (first_name, last_name, position, email, gender, employment_timestamp, workflow_step_id, workflow_id) VALUES ('arnold', 'schwarzenegger', 'kach', 'die@hard.com', 'MALE', now(), 1, 1);
INSERT INTO transition VALUES (default, 1, 1, 2);
INSERT INTO transition VALUES (default, 1, 2, null);
