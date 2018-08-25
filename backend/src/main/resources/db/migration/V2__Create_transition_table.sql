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
