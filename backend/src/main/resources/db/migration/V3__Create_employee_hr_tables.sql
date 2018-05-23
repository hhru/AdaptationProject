CREATE TYPE GENDER AS ENUM ('MALE', 'FEMALE');

CREATE TABLE personal_info
(
  id          SERIAL PRIMARY KEY,
  first_name  VARCHAR(256) NOT NULL,
  last_name   VARCHAR(256) NOT NULL,
  middle_name VARCHAR(256),
  email       VARCHAR(128) NOT NULL,
  inside      VARCHAR(64)
);

CREATE TABLE "user"
(
  id      SERIAL PRIMARY KEY,
  hhid    INT                               NOT NULL UNIQUE,
  self_id INT REFERENCES personal_info (id) NOT NULL
);

CREATE TABLE employee
(
  id              SERIAL PRIMARY KEY,
  self_id         INT REFERENCES personal_info (id)   NOT NULL,
  position        VARCHAR(256)                        NOT NULL,
  mobile_phone    BIGINT,
  internal_phone  INT,
  gender          GENDER                              NOT NULL,
  employment_date DATE                                NOT NULL,
  hr_id           INT REFERENCES "user" (id)          NOT NULL,
  mentor_id       INT REFERENCES personal_info (id),
  chief_id        INT REFERENCES personal_info (id)   NOT NULL
);

-- Some data for simplify testing of user cases
INSERT INTO personal_info (first_name, last_name, middle_name, email, inside)
VALUES ('Леонид', 'Гусев', 'Викторович', 'l.gusev@hh.ru', 'gusev');
INSERT INTO personal_info (first_name, last_name, email, inside)
VALUES ('Билл', 'Гейтс', 'gates@microsoft.com', 'gates');
INSERT INTO personal_info (first_name, last_name, middle_name, email, inside)
VALUES ('Джон', 'МакКлейн', 'МакКлейн', 'die@hard.com', 'john');
INSERT INTO "user" (hhid, self_id) VALUES (1, 2);
INSERT INTO employee (self_id, position, gender, employment_date, hr_id, mentor_id, chief_id)
VALUES (3, 'Разработчик', 'MALE', now(), 1, 1, 1);
