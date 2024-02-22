-- liquibase formatted sql

-- changeset liquibase:1
CREATE TABLE test_table (test_id INT, test_column VARCHAR, PRIMARY KEY (test_id))

-- changeset liquibase:2
CREATE TABLE test_table3 (test_id INT, test_column VARCHAR, PRIMARY KEY (test_id))