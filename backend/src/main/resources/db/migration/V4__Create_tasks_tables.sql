CREATE TABLE task_form
(
  id SERIAL PRIMARY KEY,
  employee_id INT REFERENCES employee(id) NOT NULL,
  key VARCHAR(32) UNIQUE
);

CREATE TABLE task
(
  id SERIAL PRIMARY KEY,
  task_form_id INT REFERENCES task_form(id) NOT NULL,
  text TEXT NOT NULL,
  deadline_date DATE,
  resources VARCHAR(512),
  comment VARCHAR(4096),
  is_deleted BOOLEAN DEFAULT FALSE NOT NULL
);

INSERT INTO task_form (employee_id, key) VALUES (1, '0685aca2dca7467eadbd0e6efa442351');