CREATE TABLE "user"
(
  id            SERIAL PRIMARY KEY,
  hhid          INT NOT NULL UNIQUE,
  first_name    VARCHAR(128) NOT NULL,
  last_name     VARCHAR(128) NOT NULL,
  middle_name   VARCHAR(128),
  email         VARCHAR(128) NOT NULL
);

CREATE TABLE personal_info
(
  id          SERIAL PRIMARY KEY,
  first_name  VARCHAR(256) NOT NULL,
  last_name   VARCHAR(256) NOT NULL,
  middle_name VARCHAR(256),
  email       VARCHAR(128) NOT NULL,
  inside      VARCHAR(64)
);

CREATE TABLE employee
(
  id                   SERIAL PRIMARY KEY,
  first_name           VARCHAR(128)               NOT NULL,
  last_name            VARCHAR(128)               NOT NULL,
  middle_name          VARCHAR(128),
  position             VARCHAR(256)               NOT NULL,
  email                VARCHAR(128)               NOT NULL,
  mobile_phone         BIGINT,
  internal_phone       INT,
  gender               VARCHAR(6)                 NOT NULL,
  employment_timestamp TIMESTAMP DEFAULT now()    NOT NULL,
  inside               VARCHAR(64),
  hr_id                INT REFERENCES "user" (id) NOT NULL,
  mentor_id            INT REFERENCES personal_info (id),
  chief_id             INT REFERENCES personal_info (id)   NOT NULL
);

-- Some data for simplify testing of user cases
INSERT INTO "user" (hhid, first_name, last_name, email) VALUES (1, 'Билл', 'Гейтс', 'gates@microsoft.com');
INSERT INTO "personal_info" (first_name, last_name, middle_name, email, inside)
VALUES ('Гусев', 'Леонид', 'Викторович', 'l.gusev@hh.ru', 'gusev');
INSERT INTO employee (first_name, last_name, position, email, gender, employment_timestamp, hr_id, mentor_id, chief_id)
VALUES ('Джон', 'МакКлейн', 'Охранник', 'die@hard.com', 'MALE', now(), 1, 1, 1);
