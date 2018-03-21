CREATE TABLE workflow
(
  id INT PRIMARY KEY,
  name VARCHAR(256) NOT NULL,
  description TEXT
);

CREATE TABLE workflow_set
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
  workflow_id INT REFERENCES workflow(id) NOT NULL,
  workflow_set_id INT REFERENCES workflow_set(id) NOT NULL
);

CREATE TABLE transition
(
  id SERIAL PRIMARY KEY,
  workflow_set_id INT REFERENCES workflow_set(id) NOT NULL,
  workflow_id INT REFERENCES workflow(id) NOT NULL,
  workflow_next_id INT REFERENCES workflow(id)
);

INSERT INTO workflow (id, name) VALUES (1, 'first point');
INSERT INTO workflow (id, name) VALUES (2, 'second point');
INSERT INTO workflow_set VALUES (default, 'all');
INSERT INTO employee (first_name, last_name, position, email, gender, employment_timestamp, workflow_id, workflow_set_id) VALUES ('arnold', 'schwarzenegger', 'kach', 'die@hard.com', 'MALE', now(), 1, 1);
INSERT INTO transition VALUES (default, 1, 1, 2);
INSERT INTO transition VALUES (default, 1, 2, null);
