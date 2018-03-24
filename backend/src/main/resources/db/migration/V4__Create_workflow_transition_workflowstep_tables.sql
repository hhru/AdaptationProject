CREATE TABLE workflow_step
(
  id SERIAL PRIMARY KEY,
  name VARCHAR(256) NOT NULL,
  description TEXT
);
INSERT INTO workflow_step (id, name) VALUES (1, 'Приглашение на встречу');
INSERT INTO workflow_step (id, name) VALUES (2, 'Приглашение на тимбилдинг');
INSERT INTO workflow_step (id, name) VALUES (3, 'Инквизиция');
INSERT INTO workflow_step (id, name) VALUES (4, 'Прощание');

ALTER TABLE employee ADD COLUMN workflow_step_id INT REFERENCES workflow_step(id);
UPDATE employee SET workflow_step_id = 1 WHERE id = 1;
ALTER TABLE employee ALTER COLUMN workflow_step_id SET NOT NULL;

CREATE TABLE workflow
(
  id SERIAL PRIMARY KEY,
  name VARCHAR(128)
);
INSERT INTO workflow VALUES (default, 'all');

ALTER TABLE employee ADD COLUMN workflow_id INT REFERENCES workflow(id);
UPDATE employee SET workflow_id = 1 WHERE id = 1;
ALTER TABLE employee ALTER COLUMN workflow_id SET NOT NULL;


CREATE TABLE transition
(
  id SERIAL PRIMARY KEY,
  workflow_id INT REFERENCES workflow(id) NOT NULL,
  workflow_step_id INT REFERENCES workflow_step(id) NOT NULL,
  workflow_step_next_id INT REFERENCES workflow_step(id)
);
INSERT INTO transition VALUES (default, 1, 1, 2);
INSERT INTO transition VALUES (default, 1, 2, 3);
INSERT INTO transition VALUES (default, 1, 3, 4);
INSERT INTO transition VALUES (default, 1, 4, null);

