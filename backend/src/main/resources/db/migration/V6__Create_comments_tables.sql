CREATE TABLE comment
(
  id SERIAL PRIMARY KEY,
  employee_id INT REFERENCES employee(id) NOT NULL,
  author VARCHAR(64) NOT NULL,
  message text NOT NULL
);

insert into comment (employee_id, author, message) VALUES (1, 'Uuser', 'My first comment');
