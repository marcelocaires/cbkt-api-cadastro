-- Conecta ao banco CBKT
\c cbkt;

-------------------------------
--     1) IMPORTAÇÃO CSV     --
-------------------------------

-- === ATLETA ===
TRUNCATE TABLE cadastro.atleta RESTART IDENTITY CASCADE;
\copy cadastro.atleta 
FROM '/db/atleta_202509242232.csv' 
DELIMITER ',' CSV HEADER;

-- === ATLETA_CLUBE ===
TRUNCATE TABLE cadastro.atleta_clube RESTART IDENTITY CASCADE;
\copy cadastro.atleta_clube 
FROM '/db/atleta_clube_202509242232.csv' 
DELIMITER ',' CSV HEADER;

-- === ATLETA_GRADUACAO ===
TRUNCATE TABLE cadastro.atleta_graduacao RESTART IDENTITY CASCADE;
\copy cadastro.atleta_graduacao 
FROM '/db/atleta_graduacao_202509242232.csv' 
DELIMITER ',' CSV HEADER;

-- === CLUBE ===
TRUNCATE TABLE cadastro.clube RESTART IDENTITY CASCADE;
\copy cadastro.clube 
FROM '/db/clube_202509242232.csv' 
DELIMITER ',' CSV HEADER;

-- === EXAME ===
TRUNCATE TABLE cadastro.exame RESTART IDENTITY CASCADE;
\copy cadastro.exame 
FROM '/db/exame_202509242232.csv' 
DELIMITER ',' CSV HEADER;

-- === GRADUACAO ===
TRUNCATE TABLE cadastro.graduacao RESTART IDENTITY CASCADE;
\copy cadastro.graduacao 
FROM '/db/graduacao_202509242232.csv' 
DELIMITER ',' CSV HEADER;



-------------------------------------------
--     2) AJUSTE AUTOMÁTICO DE SEQUENCES --
-------------------------------------------

-- ATLETA
SELECT setval(
    pg_get_serial_sequence('cadastro.atleta', 'codigoatleta'),
    COALESCE((SELECT MAX(codigoatleta) FROM cadastro.atleta), 1),
    true
);

-- CLUBE
SELECT setval(
    pg_get_serial_sequence('cadastro.clube', 'codigoclube'),
    COALESCE((SELECT MAX(codigoclube) FROM cadastro.clube), 1),
    true
);

-- GRADUACAO
SELECT setval(
    pg_get_serial_sequence('cadastro.graduacao', 'codigograduacao'),
    COALESCE((SELECT MAX(codigograduacao) FROM cadastro.graduacao), 1),
    true
);

-- EXAME
SELECT setval(
    pg_get_serial_sequence('cadastro.exame', 'codigoexame'),
    COALESCE((SELECT MAX(codigoexame) FROM cadastro.exame), 1),
    true
);

-- Caso atleta_clube ou atleta_graduacao venham a ter PK autogerada:
-- Basta ativar as linhas abaixo e ajustar o nome da coluna PK.

-- SELECT setval(
--     pg_get_serial_sequence('cadastro.atleta_clube', 'id'),
--     COALESCE((SELECT MAX(id) FROM cadastro.atleta_clube), 1),
--     true
-- );

-- SELECT setval(
--     pg_get_serial_sequence('cadastro.atleta_graduacao', 'id'),
--     COALESCE((SELECT MAX(id) FROM cadastro.atleta_graduacao), 1),
--     true
-- );



---------------------------------------
--     3) VERIFICAÇÃO RÁPIDA FINAL    --
---------------------------------------

-- Verifica se existe algum registro
SELECT 'ATLETA:' , COUNT(*) FROM cadastro.atleta;
SELECT 'CLUBE:' , COUNT(*) FROM cadastro.clube;
SELECT 'GRADUACAO:' , COUNT(*) FROM cadastro.graduacao;
SELECT 'EXAME:' , COUNT(*) FROM cadastro.exame;
SELECT 'ATLETA_CLUBE:' , COUNT(*) FROM cadastro.atleta_clube;
SELECT 'ATLETA_GRADUACAO:' , COUNT(*) FROM cadastro.atleta_graduacao;

-- Verifica sequences
SELECT 'ATLETA NEXTVAL:' , nextval(pg_get_serial_sequence('cadastro.atleta', 'codigoatleta'));
SELECT 'CLUBE NEXTVAL:' , nextval(pg_get_serial_sequence('cadastro.clube', 'codigoclube'));
SELECT 'GRADUACAO NEXTVAL:' , nextval(pg_get_serial_sequence('cadastro.graduacao', 'codigograduacao'));
SELECT 'EXAME NEXTVAL:' , nextval(pg_get_serial_sequence('cadastro.exame', 'codigoexame'));
