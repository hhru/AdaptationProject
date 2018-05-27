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
insert into transition (next_id, employee_id, step_type, step_status) VALUES (2, 1, 'FINAL_MEETING', 'CURRENT');
insert into transition (next_id, employee_id, step_type, step_status) VALUES (3, 1, 'INTERIM_MEETING_RESULT', 'DONE');
insert into transition (next_id, employee_id, step_type, step_status) VALUES (4, 1, 'INTERIM_MEETING', 'DONE');
insert into transition (next_id, employee_id, step_type, step_status) VALUES (5, 1, 'WELCOME_MEETING', 'DONE');
insert into transition (next_id, employee_id, step_type, step_status) VALUES (6, 1, 'TASK_LIST', 'DONE');
insert into transition (next_id, employee_id, step_type, step_status) VALUES (7, 1, 'ADD', 'DONE');

insert into transition (next_id, employee_id, step_type, step_status) VALUES (null, 2, 'QUESTIONNAIRE', 'NOT_DONE');
insert into transition (next_id, employee_id, step_type, step_status) VALUES (9, 2, 'FINAL_MEETING_RESULT', 'NOT_DONE');
insert into transition (next_id, employee_id, step_type, step_status) VALUES (10, 2, 'FINAL_MEETING', 'NOT_DONE');
insert into transition (next_id, employee_id, step_type, step_status) VALUES (11, 2, 'INTERIM_MEETING_RESULT', 'NOT_DONE');
insert into transition (next_id, employee_id, step_type, step_status) VALUES (12, 2, 'INTERIM_MEETING', 'CURRENT');
insert into transition (next_id, employee_id, step_type, step_status) VALUES (13, 2, 'WELCOME_MEETING', 'DONE');
insert into transition (next_id, employee_id, step_type, step_status) VALUES (14, 2, 'TASK_LIST', 'DONE');
insert into transition (next_id, employee_id, step_type, step_status) VALUES (15, 2, 'ADD', 'DONE');

insert into transition (next_id, employee_id, step_type, step_status) VALUES (null, 3, 'QUESTIONNAIRE', 'NOT_DONE');
insert into transition (next_id, employee_id, step_type, step_status) VALUES (17, 3, 'FINAL_MEETING_RESULT', 'NOT_DONE');
insert into transition (next_id, employee_id, step_type, step_status) VALUES (18, 3, 'FINAL_MEETING', 'NOT_DONE');
insert into transition (next_id, employee_id, step_type, step_status) VALUES (19, 3, 'INTERIM_MEETING_RESULT', 'CURRENT');
insert into transition (next_id, employee_id, step_type, step_status) VALUES (20, 3, 'INTERIM_MEETING', 'DONE');
insert into transition (next_id, employee_id, step_type, step_status) VALUES (21, 3, 'WELCOME_MEETING', 'DONE');
insert into transition (next_id, employee_id, step_type, step_status) VALUES (22, 3, 'TASK_LIST', 'DONE');
insert into transition (next_id, employee_id, step_type, step_status) VALUES (23, 3, 'ADD', 'DONE');

insert into transition (next_id, employee_id, step_type, step_status) VALUES (null, 4, 'QUESTIONNAIRE', 'NOT_DONE');
insert into transition (next_id, employee_id, step_type, step_status) VALUES (25, 4, 'FINAL_MEETING_RESULT', 'NOT_DONE');
insert into transition (next_id, employee_id, step_type, step_status) VALUES (26, 4, 'FINAL_MEETING', 'NOT_DONE');
insert into transition (next_id, employee_id, step_type, step_status) VALUES (27, 4, 'INTERIM_MEETING_RESULT', 'NOT_DONE');
insert into transition (next_id, employee_id, step_type, step_status) VALUES (28, 4, 'INTERIM_MEETING', 'NOT_DONE');
insert into transition (next_id, employee_id, step_type, step_status) VALUES (29, 4, 'WELCOME_MEETING', 'NOT_DONE');
insert into transition (next_id, employee_id, step_type, step_status) VALUES (30, 4, 'TASK_LIST', 'CURRENT');
insert into transition (next_id, employee_id, step_type, step_status) VALUES (31, 4, 'ADD', 'DONE');

insert into transition (next_id, employee_id, step_type, step_status) VALUES (null, 5, 'QUESTIONNAIRE', 'NOT_DONE');
insert into transition (next_id, employee_id, step_type, step_status) VALUES (33, 5, 'FINAL_MEETING_RESULT', 'CURRENT');
insert into transition (next_id, employee_id, step_type, step_status) VALUES (34, 5, 'FINAL_MEETING', 'DONE');
insert into transition (next_id, employee_id, step_type, step_status) VALUES (35, 5, 'INTERIM_MEETING_RESULT', 'DONE');
insert into transition (next_id, employee_id, step_type, step_status) VALUES (36, 5, 'INTERIM_MEETING', 'DONE');
insert into transition (next_id, employee_id, step_type, step_status) VALUES (37, 5, 'WELCOME_MEETING', 'DONE');
insert into transition (next_id, employee_id, step_type, step_status) VALUES (38, 5, 'TASK_LIST', 'DONE');
insert into transition (next_id, employee_id, step_type, step_status) VALUES (39, 5, 'ADD', 'DONE');
