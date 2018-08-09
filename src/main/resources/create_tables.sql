CREATE SEQUENCE public.nfe_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE public.nfe_id_seq
  OWNER TO postgres;

CREATE TABLE public.nfe
(
  id bigint NOT NULL DEFAULT nextval('nfe_id_seq'::regclass),
  nome character varying(255),
  conteudo text, 
  data_hora_emissao timestamp without time zone,
  CONSTRAINT id_pk PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.nfe
  OWNER TO postgres;