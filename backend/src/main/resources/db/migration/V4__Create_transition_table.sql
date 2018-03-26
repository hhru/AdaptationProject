CREATE TABLE transition
(
  id SERIAL PRIMARY KEY,
  employee_id INT REFERENCES employee(id),
  step VARCHAR(30) NOT NULL UNIQUE,
  step_status VARCHAR(20) NOT NULL,
  deadline_timestamp TIMESTAMP,
  comment text
);

insert into transition (employee_id, step, step_status, deadline_timestamp) VALUES (1, 'ADD', 'COMING', '2024-10-19 10:23:54');
insert into transition (employee_id, step, step_status, deadline_timestamp) VALUES (1, 'TASKLIST', 'NOTCOMING', '2025-10-19 10:23:54');
insert into transition (employee_id, step, step_status, deadline_timestamp) VALUES (1, 'WELCOMEMEETING', 'NOTCOMING', '2026-10-19 10:23:54');
insert into transition (employee_id, step, step_status, deadline_timestamp) VALUES (1, 'INTERIMMEETING', 'NOTCOMING', '2027-10-19 10:23:54');
insert into transition (employee_id, step, step_status, deadline_timestamp) VALUES (1, 'INTERIMMEETINGRESULT', 'NOTCOMING', '2028-10-19 10:23:54');
insert into transition (employee_id, step, step_status, deadline_timestamp) VALUES (1, 'FINALMEETING', 'NOTCOMING', '2029-10-19 10:23:54');
insert into transition (employee_id, step, step_status, deadline_timestamp) VALUES (1, 'FINALMEETINGRESULT', 'NOTCOMING', '2030-10-19 10:23:54');
insert into transition (employee_id, step, step_status, deadline_timestamp) VALUES (1, 'QUESTIONNAIRE', 'NOTCOMING', '2031-10-19 10:23:54');
