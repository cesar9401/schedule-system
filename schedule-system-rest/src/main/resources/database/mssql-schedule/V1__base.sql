CREATE TABLE academic_cycle (
    academic_cycle_id BIGINT NOT NULL,
    entry_date DATETIME NOT NULL,
    start_date DATETIME NOT NULL,
    end_date DATETIME NOT NULL,
    CONSTRAINT academic_cycle_pk PRIMARY KEY (academic_cycle_id)
);

CREATE TABLE ac_cy_class_day (
    ac_cy_class_day_id BIGINT NOT NULL,
    academic_cycle_id BIGINT NOT NULL,
    cat_day BIGINT NOT NULL,
    start_time DATETIME NOT NULL,
    end_time DATETIME NOT NULL,
    CONSTRAINT ac_cy_class_day_pk PRIMARY KEY (ac_cy_class_day_id)
);

CREATE TABLE subject (
    subject_id BIGINT NOT NULL,
    code VARCHAR(75) NOT NULL,
    name VARCHAR(150) NOT NULL,
    number_of_credits INT NOT NULL,
    required BIT NOT NULL,
    CONSTRAINT subject_pk PRIMARY KEY (subject_id)
);

CREATE TABLE ac_cy_subject (
    ac_cy_subject_id BIGINT NOT NULL,
    academic_cycle_id BIGINT NOT NULL,
    subject_id BIGINT NOT NULL,
    section_code VARCHAR(25) NOT NULL,
    CONSTRAINT ac_cy_subject_pk PRIMARY KEY (ac_cy_subject_id)
);

CREATE TABLE ac_cy_sub_class_day (
    ac_cy_sub_class_day_id BIGINT NOT NULL,
    cat_day BIGINT NOT NULL,
    ac_cy_subject_id BIGINT NOT NULL,
    CONSTRAINT ac_cy_sub_class_day_pk PRIMARY KEY (ac_cy_sub_class_day_id)
);

CREATE TABLE professor (
    professor_id BIGINT NOT NULL,
    full_name BIGINT NOT NULL,
    email VARCHAR(150) NOT NULL,
    date_of_hire DATETIME NOT NULL,
    CONSTRAINT professor_pk PRIMARY KEY (professor_id)
)

CREATE TABLE professor_subject (
    professor_subject_id BIGINT NOT NULL,
    professor_id BIGINT NOT NULL,
    subject_id BIGINT NOT NULL,
    CONSTRAINT professor_subject_pk PRIMARY KEY (professor_subject_id)
);

CREATE TABLE professor_contract_day (
    professor_contract_day_id BIGINT NOT NULL,
    professor_id BIGINT NOT NULL,
    cat_day BIGINT NOT NULL,
    start_time DATETIME NOT NULL,
    end_time DATETIME NOT NULL,
    CONSTRAINT professor_contract_day_pk PRIMARY KEY (professor_contract_day_id)
);

CREATE TABLE student (
    student_id BIGINT NOT NULL,
    full_name VARCHAR(200) NOT NULL,
    email VARCHAR(150) NOT NULL,
    CONSTRAINT student_pk PRIMARY KEY (student_id)
);

CREATE TABLE ac_cy_subj_assg (
    ac_cy_subj_assg_id BIGINT NOT NULL,
    ac_cy_subject_id BIGINT NOT NULL,
    student_id BIGINT NOT NULL,
    CONSTRAINT ac_cy_subj_assg_pk PRIMARY KEY (ac_cy_subj_assg_id)
);

CREATE TABLE classroom (
    classroom_id BIGINT NOT NULL,
    name VARCHAR(150) NOT NULL,
    recommended_capacity INT NOT NULL,
    CONSTRAINT classroom_pk PRIMARY KEY (classroom_id)
);

CREATE TABLE subject_schedule (
    subject_schedule_id BIGINT NOT NULL,
    ac_cy_subject_id BIGINT NOT NULL,
    professor_id BIGINT NOT NULL,
    classroom_id BIGINT NOT NULL,
    start_time DATETIME NOT NULL,
    end_time DATETIME NOT NULL,
    CONSTRAINT subject_schedule_pk PRIMARY KEY (subject_schedule_id)
);

CREATE TABLE category (
    category_id BIGINT NOT NULL,
    parent_category_id BIGINT,
    internal_id BIGINT NOT NULL,
    description VARCHAR(100) NOT NULL,
    CONSTRAINT category_pk PRIMARY KEY (category_id)
);

CREATE NONCLUSTERED INDEX category_index ON category (internal_id);

ALTER TABLE category ADD CONSTRAINT category_category_fk
FOREIGN KEY (parent_category_id)
REFERENCES category (category_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE ac_cy_subject ADD CONSTRAINT academic_cycle_ac_cy_subject_fk
FOREIGN KEY (academic_cycle_id)
REFERENCES academic_cycle (academic_cycle_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE ac_cy_class_day ADD CONSTRAINT academic_cycle_ac_cy_class_day_fk
FOREIGN KEY (academic_cycle_id)
REFERENCES academic_cycle (academic_cycle_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE professor_subject ADD CONSTRAINT subject_professor_subject_fk
FOREIGN KEY (subject_id)
REFERENCES subject (subject_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE ac_cy_subject ADD CONSTRAINT subject_ac_cy_class_day_fk
FOREIGN KEY (subject_id)
REFERENCES subject (subject_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE ac_cy_sub_class_day ADD CONSTRAINT ac_cy_class_day_ac_cy_sub_class_day_fk
FOREIGN KEY (ac_cy_subject_id)
REFERENCES ac_cy_subject (ac_cy_subject_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE ac_cy_subj_assg ADD CONSTRAINT ac_cy_subject_ac_cy_subj_assg_fk
FOREIGN KEY (ac_cy_subject_id)
REFERENCES ac_cy_subject (ac_cy_subject_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE subject_schedule ADD CONSTRAINT ac_cy_subject_subject_schedule_fk
FOREIGN KEY (ac_cy_subject_id)
REFERENCES ac_cy_subject (ac_cy_subject_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE professor_contract_day ADD CONSTRAINT professor_professor_contract_day_fk
FOREIGN KEY (professor_id)
REFERENCES professor (professor_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE professor_subject ADD CONSTRAINT professor_professor_subject_fk
FOREIGN KEY (professor_id)
REFERENCES professor (professor_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE subject_schedule ADD CONSTRAINT professor_subject_schedule_fk
FOREIGN KEY (professor_id)
REFERENCES professor (professor_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE ac_cy_subj_assg ADD CONSTRAINT student_ac_cy_subj_assg_fk
FOREIGN KEY (student_id)
REFERENCES student (student_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE subject_schedule ADD CONSTRAINT classroom_subject_schedule_fk
FOREIGN KEY (classroom_id)
REFERENCES classroom (classroom_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

-- sequences
CREATE SEQUENCE SEQ_ACADEMIC_CYCLE START WITH 10000 INCREMENT BY 1;
CREATE SEQUENCE SEQ_AC_CY_CLASS_DAY START WITH 10000 INCREMENT BY 1;
CREATE SEQUENCE SEQ_SUBJECT START WITH 10000 INCREMENT BY 1;
CREATE SEQUENCE SEQ_AC_CY_SUBJECT START WITH 10000 INCREMENT BY 1;
CREATE SEQUENCE SEQ_AC_CY_SUB_CLASS_DAY START WITH 10000 INCREMENT BY 1;
CREATE SEQUENCE SEQ_PROFESSOR START WITH 10000 INCREMENT BY 1;
CREATE SEQUENCE SEQ_PROFESSOR_SUBJECT START WITH 10000 INCREMENT BY 1;
CREATE SEQUENCE SEQ_PROFESSOR_CONTRACT_DAY START WITH 10000 INCREMENT BY 1;
CREATE SEQUENCE SEQ_STUDENT START WITH 10000 INCREMENT BY 1;
CREATE SEQUENCE SEQ_AC_CY_SUBJ_ASSG START WITH 10000 INCREMENT BY 1;
CREATE SEQUENCE SEQ_CLASSROOM START WITH 10000 INCREMENT BY 1;
CREATE SEQUENCE SEQ_SUBJECT_SCHEDULE START WITH 10000 INCREMENT BY 1;
CREATE SEQUENCE SEQ_CATEGORY START WITH 10000 INCREMENT BY 1;
