# --------------------------- ICMS

# --- !Ups

CREATE SEQUENCE ICMS_ID_SEQ;
CREATE TABLE ICMS
(
	ID INTEGER NOT NULL DEFAULT NEXTVAL('ICMS_ID_SEQ'),
	UF_ORIGEM_ID INTEGER NOT NULL,
	UF_DESTINO_ID INTEGER NOT NULL,
	ALIQUOTAINTERNA NUMERIC(5,2) NOT NULL,
	ALIQUOTAINTERESTADUAL NUMERIC(5,2) NULL,
	BASECALCULO NUMERIC(18,2) NOT NULL,
	ALIQUOTAST NUMERIC(5,2) NULL,
	BASECALCULOALIQUOTAST NUMERIC(18,2) NULL,
	NCM_ID INTEGER NULL,
	FOREIGN KEY (UF_ORIGEM_ID) REFERENCES UF(ID),
	FOREIGN KEY (UF_DESTINO_ID) REFERENCES UF(ID)
);

# --- !Downs

DROP TABLE ICMS;
DROP SEQUENCE ICMS_ID_SEQ;