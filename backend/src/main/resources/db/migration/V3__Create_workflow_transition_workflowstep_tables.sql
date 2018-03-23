CREATE TABLE workflow_step
(
  id SERIAL PRIMARY KEY,
  name VARCHAR(256) NOT NULL,
  description TEXT
);

CREATE TABLE workflow
(
  id SERIAL PRIMARY KEY,
  name VARCHAR(128)
);

CREATE TABLE transition
(
  id SERIAL PRIMARY KEY,
  workflow_id INT REFERENCES workflow(id) NOT NULL,
  workflow_step_id INT REFERENCES workflow_step(id) NOT NULL,
  workflow_step_next_id INT REFERENCES workflow_step(id)
);

INSERT INTO workflow_step VALUES (default, 'Приглашение на встречу');
INSERT INTO workflow_step VALUES (default, 'Приглашение на тимбилдинг');
INSERT INTO workflow_step VALUES (default, 'Инквизиция');
INSERT INTO workflow_step VALUES (default, 'Прощание');
INSERT INTO workflow VALUES (default, 'all');
INSERT INTO employee (first_name, last_name, position, email, gender, employment_timestamp, workflow_step_id, workflow_id) VALUES ('arnold', 'schwarzenegger', 'kach', 'die@hard.com', 'MALE', now(), 1, 1);
INSERT INTO transition VALUES (default, 1, 1, 2);
INSERT INTO transition VALUES (default, 1, 2, 3);
INSERT INTO transition VALUES (default, 1, 3, 4);
INSERT INTO transition VALUES (default, 1, 4, null);
