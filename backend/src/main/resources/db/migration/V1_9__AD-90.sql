CREATE TYPE scheduled_mail_type AS ENUM ('CUSTOM', 'WELCOME', 'CHIEF_TASK', 'PROBATION_RESULT');

ALTER TABLE scheduled_mail ADD COLUMN mail_subject VARCHAR(256);
ALTER TABLE scheduled_mail ADD COLUMN mail_text text;

ALTER TABLE scheduled_mail ADD COLUMN mail_type scheduled_mail_type;
UPDATE scheduled_mail SET mail_type = 'WELCOME';
ALTER TABLE scheduled_mail ALTER COLUMN mail_type SET NOT NULL;

ALTER TABLE scheduled_mail ADD COLUMN recipients VARCHAR(2048);
