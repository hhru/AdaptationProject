CREATE TYPE workflow_step_type AS ENUM ('ADD', 'TASK_LIST', 'WELCOME_MEETING', 'INTERIM_MEETING', 'INTERIM_MEETING_RESULT', 'FINAL_MEETING', 'FINAL_MEETING_RESULT', 'QUESTIONNAIRE');

CREATE TYPE workflow_step_status AS ENUM ('DONE', 'CURRENT', 'NOT_DONE', 'IGNORE');

CREATE TABLE transition
(
  id SERIAL PRIMARY KEY,
  next_id INT REFERENCES transition(id),
  employee_id INT REFERENCES employee(id) NOT NULL,
  step_type workflow_step_type NOT NULL,
  step_status workflow_step_status NOT NULL,
  deadline_timestamp TIMESTAMP,
  comment text
);

insert into transition (next_id, employee_id, step_type, step_status) VALUES (null, 1, 'QUESTIONNAIRE', 'NOT_DONE');
insert into transition (next_id, employee_id, step_type, step_status) VALUES (1, 1, 'FINAL_MEETING_RESULT', 'NOT_DONE');
insert into transition (next_id, employee_id, step_type, step_status) VALUES (2, 1, 'FINAL_MEETING', 'NOT_DONE');
insert into transition (next_id, employee_id, step_type, step_status) VALUES (3, 1, 'INTERIM_MEETING_RESULT', 'NOT_DONE');
insert into transition (next_id, employee_id, step_type, step_status) VALUES (4, 1, 'INTERIM_MEETING', 'NOT_DONE');
insert into transition (next_id, employee_id, step_type, step_status) VALUES (5, 1, 'WELCOME_MEETING', 'NOT_DONE');
insert into transition (next_id, employee_id, step_type, step_status) VALUES (6, 1, 'TASK_LIST', 'NOT_DONE');
insert into transition (next_id, employee_id, step_type, step_status) VALUES (7, 1, 'ADD', 'CURRENT');
