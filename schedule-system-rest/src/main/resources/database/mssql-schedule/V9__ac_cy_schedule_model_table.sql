CREATE TABLE ac_cy_schedule_model (
    ac_cy_schedule_model_id BIGINT NOT NULL,
    academic_cycle_id BIGINT NOT NULL,
    responsible_user VARCHAR(100) NOT NULL,
    description VARCHAR(100) NOT NULL,
    entry_date DATETIME NOT NULL,
    CONSTRAINT ac_cy_schedule_model_pk PRIMARY KEY (ac_cy_schedule_model_id)
);

-- drop relationship between academic_cycle and ac_cy_schedule
ALTER TABLE ac_cy_schedule DROP CONSTRAINT IF EXISTS academic_cycle_ac_cy_schedule_fk;
-- drop column in ac_cy_schedule
ALTER TABLE ac_cy_schedule DROP COLUMN academic_cycle_id;
-- add column in ac_cy_schedule
ALTER TABLE ac_cy_schedule ADD ac_cy_schedule_model_id BIGINT CONSTRAINT ac_cy_schedule_ac_cy_schedule_model_id_default DEFAULT 0 NOT NULL;

-- add new constraints
ALTER TABLE ac_cy_schedule_model ADD CONSTRAINT academic_cycle_ac_cy_schedule_model_fk
FOREIGN KEY (academic_cycle_id)
REFERENCES academic_cycle (academic_cycle_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE ac_cy_schedule ADD CONSTRAINT ac_cy_schedule_model_ac_cy_schedule_fk
FOREIGN KEY (ac_cy_schedule_model_id)
REFERENCES ac_cy_schedule_model (ac_cy_schedule_model_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

CREATE SEQUENCE SEQ_AC_CY_SCHEDULE_MODEL START WITH 10000 INCREMENT BY 1;
