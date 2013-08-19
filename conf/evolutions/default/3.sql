# UF schema
 
# --- !Ups

CREATE SEQUENCE uf_id_seq;
CREATE TABLE uf (
    id integer NOT NULL DEFAULT nextval('uf_id_seq'),
	nome varchar(50)
);
 
# --- !Downs
 
DROP TABLE uf;
DROP SEQUENCE uf_id_seq;


INSERT INTO uf(nome) VALUES ("AC");
INSERT INTO uf(nome) VALUES ("AL");
INSERT INTO uf(nome) VALUES ("AP");
INSERT INTO uf(nome) VALUES ("AM");
INSERT INTO uf(nome) VALUES ("BA");
INSERT INTO uf(nome) VALUES ("CE");
INSERT INTO uf(nome) VALUES ("DF");
INSERT INTO uf(nome) VALUES ("ES");
INSERT INTO uf(nome) VALUES ("GO");
INSERT INTO uf(nome) VALUES ("MA");
INSERT INTO uf(nome) VALUES ("MT");
INSERT INTO uf(nome) VALUES ("MS");
INSERT INTO uf(nome) VALUES ("MG");
INSERT INTO uf(nome) VALUES ("PA");
INSERT INTO uf(nome) VALUES ("PB");
INSERT INTO uf(nome) VALUES ("PR");
INSERT INTO uf(nome) VALUES ("PE");
INSERT INTO uf(nome) VALUES ("PI");
INSERT INTO uf(nome) VALUES ("RJ");
INSERT INTO uf(nome) VALUES ("RN");
INSERT INTO uf(nome) VALUES ("RS");
INSERT INTO uf(nome) VALUES ("RO");
INSERT INTO uf(nome) VALUES ("RR");
INSERT INTO uf(nome) VALUES ("SC");
INSERT INTO uf(nome) VALUES ("SP");
INSERT INTO uf(nome) VALUES ("SE");
INSERT INTO uf(nome) VALUES ("TO");