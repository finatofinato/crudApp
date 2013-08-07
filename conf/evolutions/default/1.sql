# Persons schema
 
# --- !Ups

CREATE SEQUENCE person_id_seq;
CREATE TABLE person (
    	id integer NOT NULL DEFAULT nextval('person_id_seq'),
	name varchar(10),
	born_date timestamp
);
 
# --- !Downs
 
DROP TABLE person;
DROP SEQUENCE person_id_seq;
