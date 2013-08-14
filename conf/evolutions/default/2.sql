# Fornecedor schema
 
# --- !Ups

CREATE SEQUENCE fornecedor_id_seq;
CREATE TABLE fornecedor (
    id integer NOT NULL DEFAULT nextval('fornecedor_id_seq'),
	nome varchar(100),
	cnpj varchar(14),
	cpf varchar(11),
	nome_fantasia varchar(100),
	endereco varchar(100),
	complemento varchar(100),
	bairro varchar(50),
	cep varchar(8),
	uf varchar(2),
	cidade varchar(50),
	ie varchar(15),
	im varchar(15),
	fone varchar(12),
	data_cadastro timestamp
);
 
# --- !Downs
 
DROP TABLE fornecedor;
DROP SEQUENCE fornecedor_id_seq;
