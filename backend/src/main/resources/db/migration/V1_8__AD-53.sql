ALTER TABLE task ADD COLUMN deadline VARCHAR(512);
update task set deadline = to_char(deadline_date, 'DD.MM.YYYY');

ALTER TABLE task ADD COLUMN is_weeks BOOLEAN DEFAULT FALSE NOT NULL;
