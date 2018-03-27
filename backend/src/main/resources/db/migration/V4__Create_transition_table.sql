CREATE TYPE workflow_step_type AS ENUM ('ADD', 'TASK_LIST', 'WELCOME_MEETING', 'INTERIM_MEETING', 'INTERIM_MEETING_RESULT', 'FINAL_MEETING', 'FINAL_MEETING_RESULT', 'QUESTIONNAIRE');

CREATE TABLE workflow_step
(
  id SERIAL PRIMARY KEY,
  name workflow_step_type NOT NULL
);
insert into workflow_step (name) VALUES ('ADD');
insert into workflow_step (name) VALUES ('TASK_LIST');
insert into workflow_step (name) VALUES ('WELCOME_MEETING');
insert into workflow_step (name) VALUES ('INTERIM_MEETING');
insert into workflow_step (name) VALUES ('INTERIM_MEETING_RESULT');
insert into workflow_step (name) VALUES ('FINAL_MEETING');
insert into workflow_step (name) VALUES ('FINAL_MEETING_RESULT');
insert into workflow_step (name) VALUES ('QUESTIONNAIRE');

CREATE TYPE workflow_step_status AS ENUM ('DONE', 'CURRENT', 'NOT_DONE', 'OVERDUE', 'IGNORE');
CREATE TABLE transition
(
  id SERIAL PRIMARY KEY,
  employee_id INT REFERENCES employee(id),
  workflow_step_id INT REFERENCES workflow_step(id),
  step_status workflow_step_status NOT NULL,
  deadline_timestamp TIMESTAMP,
  comment text
);

insert into transition (employee_id, workflow_step_id, step_status) VALUES (1, 1, 'CURRENT');
insert into transition (employee_id, workflow_step_id, step_status) VALUES (1, 2, 'NOT_DONE');
insert into transition (employee_id, workflow_step_id, step_status) VALUES (1, 3, 'NOT_DONE');
insert into transition (employee_id, workflow_step_id, step_status) VALUES (1, 4, 'NOT_DONE');
insert into transition (employee_id, workflow_step_id, step_status) VALUES (1, 5, 'NOT_DONE');
insert into transition (employee_id, workflow_step_id, step_status) VALUES (1, 6, 'NOT_DONE');
insert into transition (employee_id, workflow_step_id, step_status) VALUES (1, 7, 'NOT_DONE');
insert into transition (employee_id, workflow_step_id, step_status) VALUES (1, 8, 'NOT_DONE');
