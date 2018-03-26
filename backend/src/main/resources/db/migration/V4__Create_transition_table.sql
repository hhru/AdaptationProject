CREATE TYPE workflow_step AS ENUM ('Add','TaskList', 'WelcomeMeeting', 'InterimMeeting', 'InterimMeetingResult', 'FinalMeeting', 'FinalMeetingResult', 'Questionnaire');

CREATE TYPE workflow_step_status AS ENUM ('Done', 'Coming', 'Notcoming', 'Overdue', 'Ignore');

CREATE TABLE transition
(
  id SERIAL PRIMARY KEY,
  employee_id INT REFERENCES employee(id),
  step workflow_step default 'Add' NOT NULL,
  step_status workflow_step_status default 'Coming' NOT NULL,
  deadline_timestamp TIMESTAMP,
  comment text
);
insert into transition (employee_id, deadline_timestamp) VALUES (1, '2024-10-19 10:23:54');
