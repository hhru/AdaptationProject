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

insert into comment (employee_id, author, message) VALUES (1, 1, 'My first comment');
insert into log (employee_id, author, message, event_date) VALUES (1, 'Uuser', 'My first log', now());
