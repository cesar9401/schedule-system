ALTER TABLE dbo.professor ADD average_qualification NUMERIC(5, 2)
CONSTRAINT professor_average_qualification_default DEFAULT 0 NOT NULL;

ALTER TABLE dbo.professor_subject ADD qualification NUMERIC(5, 2)
CONSTRAINT professor_subject_qualification_default DEFAULT 0 NOT NULL;

ALTER TABLE dbo.professor_subject ADD years_of_experience INTEGER
CONSTRAINT professor_subject_years_of_experience_default DEFAULT 0 NOT NULL;

ALTER TABLE dbo.ac_cy_subject ADD priority INTEGER
CONSTRAINT ac_cy_subject_cat_priority_default DEFAULT 0 NOT NULL;

ALTER TABLE dbo.student ADD number_of_credits INTEGER
CONSTRAINT student_number_of_credits_default DEFAULT 0 NOT NULL;

ALTER TABLE dbo.student ADD average NUMERIC (5, 2)
CONSTRAINT student_average_default DEFAULT 0 NOT NULL;

ALTER TABLE dbo.subject ADD subject_index NUMERIC (7, 5)
CONSTRAINT subject_subject_index DEFAULT 0 NOT NULL;
