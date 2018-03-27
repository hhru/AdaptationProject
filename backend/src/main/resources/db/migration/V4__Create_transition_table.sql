CREATE TABLE workflow_step
(
  id SERIAL PRIMARY KEY,
  name VARCHAR(30) NOT NULL
);
insert into workflow_step (name) VALUES ('ADD');
insert into workflow_step (name) VALUES ('TASK_LIST');
insert into workflow_step (name) VALUES ('WELCOME_MEETING');
insert into workflow_step (name) VALUES ('INTERIM_MEETING');
insert into workflow_step (name) VALUES ('INTERIM_MEETING_RESULT');
insert into workflow_step (name) VALUES ('FINAL_MEETING');
insert into workflow_step (name) VALUES ('FINAL_MEETING_RESULT');
insert into workflow_step (name) VALUES ('QUESTIONNAIRE');

CREATE TABLE transition
(
  id SERIAL PRIMARY KEY,
  employee_id INT REFERENCES employee(id),
  workflow_step_id INT REFERENCES workflow_step(id),
  step_status VARCHAR(20) NOT NULL,
  deadline_timestamp TIMESTAMP,
  comment text
);

insert into transition (employee_id, workflow_step_id, step_status, deadline_timestamp) VALUES (1, 1, 'CURRENT', '2024-10-19 10:23:54');
insert into transition (employee_id, workflow_step_id, step_status, deadline_timestamp) VALUES (1, 2, 'NOT_DONE', '2025-10-19 10:23:54');
insert into transition (employee_id, workflow_step_id, step_status, deadline_timestamp) VALUES (1, 3, 'NOT_DONE', '2026-10-19 10:23:54');
insert into transition (employee_id, workflow_step_id, step_status, deadline_timestamp) VALUES (1, 4, 'NOT_DONE', '2027-10-19 10:23:54');
insert into transition (employee_id, workflow_step_id, step_status, deadline_timestamp) VALUES (1, 5, 'NOT_DONE', '2028-10-19 10:23:54');
insert into transition (employee_id, workflow_step_id, step_status, deadline_timestamp) VALUES (1, 6, 'NOT_DONE', '2029-10-19 10:23:54');
insert into transition (employee_id, workflow_step_id, step_status, deadline_timestamp) VALUES (1, 7, 'NOT_DONE', '2030-10-19 10:23:54');
insert into transition (employee_id, workflow_step_id, step_status, deadline_timestamp) VALUES (1, 8, 'NOT_DONE', '2031-10-19 10:23:54');
