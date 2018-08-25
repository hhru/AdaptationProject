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
