ALTER TABLE dbo.academic_cycle ADD name VARCHAR(100) CONSTRAINT academic_cycle_name_default DEFAULT '' NOT NULL;
ALTER TABLE dbo.academic_cycle ADD description VARCHAR(150) CONSTRAINT academic_cycle_description_default DEFAULT '' NOT NULL;

ALTER TABLE dbo.ac_cy_subject ADD assigned_students BIGINT CONSTRAINT ac_cy_subject_assigned_students_default DEFAULT 1 NOT NUll;
