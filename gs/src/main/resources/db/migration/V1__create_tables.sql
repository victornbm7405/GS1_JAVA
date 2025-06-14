-- ===============================================================
-- V1__create_tables.sql
-- Cria as tabelas GS_CIDADE e GS_MENSAGEM (alerta) no Oracle 19c
-- ===============================================================

-- 1) Tabela de cidades
CREATE TABLE GS_CIDADE (
                           ID          NUMBER                       GENERATED BY DEFAULT ON NULL AS IDENTITY PRIMARY KEY,
                           NOME        VARCHAR2(100 CHAR)           NOT NULL,
                           ESTADO      VARCHAR2(2 CHAR)             NOT NULL,
                           LATITUDE    NUMBER(10,6),
                           LONGITUDE   NUMBER(10,6)
);

-- 2) Tabela de alertas (mensagens geradas para cada cidade)
CREATE TABLE GS_MENSAGEM (
                             ID           NUMBER                       GENERATED BY DEFAULT ON NULL AS IDENTITY PRIMARY KEY,
                             TEMPERATURA  NUMBER(5,2),
                             NIVEL_RISCO  VARCHAR2(10 CHAR)            NOT NULL,
                             MENSAGEM     VARCHAR2(255 CHAR)           NOT NULL,
                             CIDADE_ID    NUMBER                       NOT NULL,
                             CONSTRAINT FK_MENSAGEM_CIDADE
                                 FOREIGN KEY (CIDADE_ID)
                                     REFERENCES GS_CIDADE (ID)
);
