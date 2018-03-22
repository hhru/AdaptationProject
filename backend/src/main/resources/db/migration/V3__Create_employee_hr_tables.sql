CREATE TABLE "user"
(
  id SERIAL PRIMARY KEY,
  hhid INT NOT NULL,
  first_name VARCHAR(128)                         NOT NULL,
  last_name VARCHAR(128)                          NOT NULL,
  middle_name VARCHAR(128)
);

CREATE TABLE employee
(
  id SERIAL PRIMARY KEY,
  first_name VARCHAR(128)                         NOT NULL,
  last_name VARCHAR(128)                          NOT NULL,
  middle_name VARCHAR(128),
  position VARCHAR(256)                           NOT NULL,
  email VARCHAR(128)                              NOT NULL,
  mobile_phone BIGINT,
  internal_phone INT,
  gender VARCHAR(6)                              NOT NULL,
  employment_timestamp TIMESTAMP DEFAULT now() NOT NULL,
  mentor_id INT REFERENCES "user" (id) NOT NULL,
  chief_id INT REFERENCES "user" (id) NOT NULL
);

-- Some data for simplify testing of user cases
INSERT INTO "user" (hhid, first_name, last_name) VALUES (1, 'Билл', 'Гейтс');
INSERT INTO employee (first_name, last_name, position, email, gender, employment_timestamp, mentor_id, chief_id)
    VALUES ('Джон', 'МакКлейн', 'Охранник', 'die@hard.com', 'MALE', now(), 1, 1);
