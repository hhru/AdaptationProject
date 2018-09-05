ALTER TABLE employee ADD COLUMN interim_date DATE;
update employee set interim_date = employment_date + 45 where interim_date is null;
ALTER TABLE employee ALTER COLUMN interim_date SET NOT NULL;

ALTER TABLE employee ADD COLUMN final_date DATE;
update employee set final_date = employment_date + 90 where final_date is null;
ALTER TABLE employee ALTER COLUMN final_date SET NOT NULL;
