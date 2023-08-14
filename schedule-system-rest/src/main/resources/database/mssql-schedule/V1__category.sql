CREATE TABLE category (
    category_id BIGINT NOT NULL,
    parent_category_id BIGINT NOT NULL,
    internal_id BIGINT NOT NULL,
    description VARCHAR(100) NOT NULL,
    CONSTRAINT category_pk PRIMARY KEY (category_id)
);

CREATE NONCLUSTERED INDEX category_index ON dbo.category (internal_id);

ALTER TABLE category ADD CONSTRAINT category_category_fk
FOREIGN KEY (parent_category_id)
REFERENCES category (category_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;
