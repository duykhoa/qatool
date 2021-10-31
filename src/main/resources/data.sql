DROP TABLE IF EXISTS schema_columns;
DROP TABLE IF EXISTS schemas;

CREATE TABLE schemas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR NOT NULL
);

CREATE TABLE schema_columns (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR NOT NULL,
    type VARCHAR NOT NULL,
    condition_class VARCHAR,
    schema_id INT NOT NULL,
    CONSTRAINT schemas_fk_1 FOREIGN KEY (schema_id) REFERENCES schemas (id)
);