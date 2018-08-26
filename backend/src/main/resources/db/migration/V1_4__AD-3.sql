CREATE TABLE comment
(
  id SERIAL PRIMARY KEY,
  employee_id INT REFERENCES employee(id) NOT NULL,
  author INT REFERENCES "user"(id) NOT NULL,
  message text NOT NULL
);

CREATE TABLE log
(
  id SERIAL PRIMARY KEY,
  employee_id INT REFERENCES employee(id) NOT NULL,
  author VARCHAR(64) NOT NULL,
  message text NOT NULL,
  event_date DATE NOT NULL
);
