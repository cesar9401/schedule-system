CREATE TABLE ac_cy_schedule (
    ac_cy_schedule_id BIGINT NOT NULL,
    academic_cycle_id BIGINT NOT NULL,
    is_valid BIT NOT NULL,
    CONSTRAINT ac_cy_schedule_pk PRIMARY KEY (ac_cy_schedule_id)
);

ALTER TABLE subject_schedule DROP CONSTRAINT ac_cy_subject_subject_schedule_fk;
ALTER TABLE subject_schedule DROP CONSTRAINT professor_subject_schedule_fk;
ALTER TABLE subject_schedule DROP CONSTRAINT classroom_subject_schedule_fk;
ALTER TABLE subject_schedule DROP CONSTRAINT subject_schedule_pk;

-- renaming subject_schedule to ac_cy_sched_subj
EXEC sp_rename 'dbo.subject_schedule', 'ac_cy_sched_subj';
EXEC sp_rename 'dbo.ac_cy_sched_subj.subject_schedule_id', 'ac_cy_sched_subj_id', 'COLUMN';
EXEC sp_rename 'DBO.SEQ_SUBJECT_SCHEDULE', 'SEQ_AC_CY_SCHED_SUBJ';

ALTER TABLE ac_cy_sched_subj ADD ac_cy_schedule_id BIGINT NOT NULL;
ALTER TABLE ac_cy_sched_subj ADD CONSTRAINT ac_cy_sched_subj_pk PRIMARY KEY (ac_cy_sched_subj_id);

ALTER TABLE ac_cy_sched_subj ADD CONSTRAINT ac_cy_schedule_ac_cy_sched_subj_fk
FOREIGN KEY (ac_cy_schedule_id)
REFERENCES ac_cy_schedule (ac_cy_schedule_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE ac_cy_sched_subj ADD CONSTRAINT ac_cy_subject_ac_cy_sched_subj_fk
FOREIGN KEY (ac_cy_subject_id)
REFERENCES ac_cy_subject (ac_cy_subject_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE ac_cy_sched_subj ADD CONSTRAINT professor_ac_cy_sched_subj_fk
FOREIGN KEY (professor_id)
REFERENCES professor (professor_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE ac_cy_sched_subj ADD CONSTRAINT classroom_ac_cy_sched_subj_fk
FOREIGN KEY (classroom_id)
REFERENCES classroom (classroom_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE ac_cy_schedule ADD CONSTRAINT academic_cycle_ac_cy_schedule_fk
FOREIGN KEY (academic_cycle_id)
REFERENCES academic_cycle (academic_cycle_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

CREATE SEQUENCE SEQ_AC_CY_SCHEDULE START WITH 10000 INCREMENT BY 1;
