CREATE TABLE scheduled_mail
(
  id              SERIAL PRIMARY KEY,
  trigger_date    TIMESTAMP NOT NULL,
  employee_id     INT REFERENCES employee(id) NOT NULL
);
