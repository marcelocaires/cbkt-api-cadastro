-- ============================================================================
-- POPULAÇÃO INICIAL DA TABELA CARGO COM TIPOS DE DIRETORIA
-- ============================================================================
-- Cargos genéricos usados em Clubes, Federações e Confederações
-- A estrutura específica por entidade é definida na COMPOSICAO_MANDATO
--
-- FEDERAÇÃO/CONFEDERAÇÃO: Presidente, Vice-Presidente, Diretor Técnico, 
--   Diretor Secretário, Diretor Jurídico, Conselho Fiscal, Suplentes, Diretor Financeiro
--
-- CLUBES: Instrutor Chefe, Presidente, Diretor Técnico, Secretário, Tesoureiro

INSERT INTO CARGO (NOME, HIERARQUIA, REQUER_SUPLENTE, ATIVO)
VALUES
    ('Presidente', 1, FALSE, TRUE),
    ('Vice-Presidente', 2, TRUE, TRUE),
    ('Diretor Técnico', 3, FALSE, TRUE),
    ('Diretor Secretário', 4, FALSE, TRUE),
    ('Diretor Jurídico', 5, FALSE, TRUE),
    ('Conselho Fiscal', 6, TRUE, TRUE),
    ('Suplentes', 7, FALSE, TRUE),
    ('Diretor Financeiro', 8, FALSE, TRUE),
    ('Instrutor Chefe', 9, FALSE, TRUE),
    ('Secretário', 10, FALSE, TRUE),
    ('Tesoureiro', 11, FALSE, TRUE)
ON CONFLICT (NOME) DO NOTHING;

-- ============================================================================
-- VALIDAÇÃO - Listar cargos inseridos
-- ============================================================================
SELECT 
    COUNT(*) as total_cargos,
    STRING_AGG(NOME, ', ' ORDER BY HIERARQUIA) as cargos
FROM CARGO
WHERE ATIVO = TRUE;
