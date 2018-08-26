CREATE TABLE questionnaire
(
  id SERIAL PRIMARY KEY,
  employee_id INT REFERENCES employee(id) NOT NULL,
  key VARCHAR(32) UNIQUE,
  is_complete boolean NOT NULL
);

CREATE TABLE questionnaire_answer
(
  id SERIAL PRIMARY KEY,
  questionnaire_id INT REFERENCES questionnaire(id) NOT NULL,
  quest_number INT NOT NULL,
  answer_number INT,
  answer_text VARCHAR(512)
);

