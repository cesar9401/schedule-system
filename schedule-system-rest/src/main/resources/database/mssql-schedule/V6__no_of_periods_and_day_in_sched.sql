-- number of periods
ALTER TABLE dbo.ac_cy_subject ADD number_of_periods BIGINT CONSTRAINT ac_cy_subject_number_of_periods_default DEFAULT 1 NOT NULL;
-- day of the week for the schedule
ALTER TABLE dbo.ac_cy_sched_subj ADD cat_day BIGINT CONSTRAINT ac_cy_sched_subj_cat_day_default DEFAULT 1 NOT NULL;
