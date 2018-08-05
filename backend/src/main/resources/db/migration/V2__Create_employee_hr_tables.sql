CREATE TYPE GENDER AS ENUM ('MALE', 'FEMALE');
CREATE TYPE ACCESS_TYPE AS ENUM ('ADMIN', 'WHITELIST', 'OTHER');

CREATE TABLE personal_info
(
  id          SERIAL PRIMARY KEY,
  first_name  VARCHAR(256) NOT NULL,
  last_name   VARCHAR(256) NOT NULL,
  middle_name VARCHAR(256),
  email       VARCHAR(128) NOT NULL,
  inside      VARCHAR(64),
  subdivision VARCHAR(128)
);

CREATE TABLE access_rule
(
  id      SERIAL PRIMARY KEY,
  hhid    INT                               NOT NULL UNIQUE,
  access_type ACCESS_TYPE NOT NULL DEFAULT 'OTHER'
);

CREATE TABLE "user"
(
  id      SERIAL PRIMARY KEY,
  access_rule_id    INT REFERENCES access_rule(id)  NOT NULL UNIQUE,
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
  chief_id        INT REFERENCES personal_info (id)   NOT NULL,
  dismissed       BOOLEAN DEFAULT FALSE
);

-- employees
INSERT INTO personal_info (first_name, last_name, middle_name, email, inside, subdivision)
VALUES ('Марфа', 'Ябурова', 'Германовна', 'm.yaburova@hh.ru', 'yaburova', 'техдеп');
INSERT INTO personal_info (first_name, last_name, middle_name, email, inside, subdivision)
VALUES ('Лавр', 'Рыков', 'Еремеевич', 'l.rukov@hh.ru', 'rykov', 'эйчары');
INSERT INTO personal_info (first_name, last_name, middle_name, email, inside, subdivision)
VALUES ('Федор', 'Савинков', 'Олегович', 'f.savinkov@hh.ru', 'savinkov', 'qa');
INSERT INTO personal_info (first_name, last_name, middle_name, email, inside, subdivision)
VALUES ('Берта', 'Андрюхина', 'Давидовна', 'b.andryhina@hh.ru', 'andryhina', 'эйчары');
INSERT INTO personal_info (first_name, last_name, middle_name, email, inside, subdivision)
VALUES ('Григорий', 'Харьков', 'Ираклиевич', 'g.harkov@hh.ru', 'harkov', 'техдеп');

-- mentors
INSERT INTO personal_info (first_name, last_name, middle_name, email, inside)
VALUES ('Вероника', 'Есаулова', 'Семеновна', 'v.esaulova@hh.ru', 'esaulova');
INSERT INTO personal_info (first_name, last_name, middle_name, email, inside)
VALUES ('Мартын', 'Пузанов', 'Зиновиевич', 'm.puzanov@hh.ru', 'puzanov');
INSERT INTO personal_info (first_name, last_name, middle_name, email, inside)
VALUES ('Яков', 'Гнусарев', 'Маркович', 'ya.gnusarev@hh.ru', 'gnusarev');

-- hr
INSERT INTO personal_info (first_name, last_name, middle_name, email, inside)
VALUES ('Ника', 'Панфилова', 'Феликсовна', 'super.hr.manager@gmail.com', 'panfilova');
INSERT INTO personal_info (first_name, last_name, middle_name, email, inside)
VALUES ('Роза', 'Яловкина', 'Василиевна', 'adaptation.hh@gmail.com', 'yalovkina');

INSERT INTO access_rule (hhid) VALUES (1);
INSERT INTO access_rule (hhid) VALUES (2);

INSERT INTO "user" (access_rule_id, self_id) VALUES (1, 9);
INSERT INTO "user" (access_rule_id, self_id) VALUES (2, 10);

INSERT INTO employee (self_id, position, gender, employment_date, hr_id, mentor_id, chief_id)
VALUES (1, 'Разработчик', 'FEMALE', '2018-04-28', 1, 6, 7);
INSERT INTO employee (self_id, position, gender, employment_date, hr_id, mentor_id, chief_id)
VALUES (2, 'Менеджер', 'MALE', '2018-05-05', 1, 8, 6);
INSERT INTO employee (self_id, position, gender, employment_date, hr_id, mentor_id, chief_id)
VALUES (3, 'Менеджер', 'MALE', '2018-05-04', 1, 8, 6);
INSERT INTO employee (self_id, position, gender, employment_date, hr_id, mentor_id, chief_id)
VALUES (4, 'Охранник', 'FEMALE', '2018-05-07', 2, 7, 8);
INSERT INTO employee (self_id, position, gender, employment_date, hr_id, mentor_id, chief_id)
VALUES (5, 'Разработчик', 'MALE', '2018-05-10', 2, 6, 7);
