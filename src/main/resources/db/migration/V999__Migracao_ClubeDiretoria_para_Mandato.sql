-- ============================================================================
-- MIGRAÇÃO DE ClubeDiretoria PARA MANDATO E MANDATO_CARGO
-- ============================================================================
-- Este script migra os dados de dirigentes armazenados em CLUBE para a nova
-- estrutura de mandatos e cargos.
--
-- Mapeamento:
--   CLUBE.RESPONSAVEL -> Pessoa (tipo INTEGRANTE) -> MANDATO_CARGO (Instrutor Chefe/TITULAR/ELEITO)
--   CLUBE.DIRETORTECNICO -> Pessoa (tipo INTEGRANTE) -> MANDATO_CARGO (Diretor Técnico/TITULAR/ELEITO)
--   CLUBE.PRESIDENTE -> Pessoa (tipo INTEGRANTE) -> MANDATO_CARGO (Presidente/TITULAR/ELEITO)
--
-- Data de início: CLUBE.DATAFUNDACAO ou 2024-01-01 se null
-- Relacionamento: CLUBE não tem FK para MANDATO, logo o relacionamento é feito via TIPO_ENTIDADE + ENTIDADE_ID
-- ============================================================================

-- ============================================================================
-- PASSO 1: Criar MANDATO para cada clube (um por clube)
-- ============================================================================
INSERT INTO MANDATO (
    TIPO_ENTIDADE, 
    ENTIDADE_ID, 
    DATA_INICIO, 
    DATA_FIM, 
    ATIVO, 
    DESCRICAO, 
    DOCUMENTO_PATH,
    CRIADO_EM, 
    ATUALIZADO_EM, 
    CRIADO_POR_CPF, 
    CRIADO_POR_NOME,
    ATUALIZADO_POR_CPF,
    ATUALIZADO_POR_NOME,
    VERSAO
)
SELECT 
    'CLUBE',
    c.CODIGOCLUBE,
    COALESCE(c.DATAFUNDACAO, '2024-01-01'::date),
    NULL::date,
    TRUE,
    'Mandato migrado da tabela CLUBE - Gestão atual',
    NULL,
    NOW(),
    NOW(),
    '00000000000',
    'SCRIPT-MIGRACAO',
    '00000000000',
    'SCRIPT-MIGRACAO',
    0
FROM CLUBE c
WHERE c.CODIGOCLUBE NOT IN (
    SELECT ENTIDADE_ID FROM MANDATO 
    WHERE TIPO_ENTIDADE = 'CLUBE'
)
AND (
    c.PRESIDENTE IS NOT NULL 
    OR c.DIRETORTECNICO IS NOT NULL 
    OR c.RESPONSAVEL IS NOT NULL
);

-- ============================================================================
-- PASSO 2: Criar PESSOA para cada dirigente (evitando duplicatas)
-- ============================================================================

-- PRESIDENTE
INSERT INTO PESSOA (NOME, CPF, RG, DATA_NASCIMENTO, EMAIL, TELEFONE, TIPO, ATIVO)
SELECT DISTINCT
    c.PRESIDENTE,
    -- Gerar CPF fictício único baseado no hash do nome
    LPAD(SUBSTRING(MD5(UPPER(c.PRESIDENTE)) FROM 1 FOR 11), 11, '0'),
    NULL,
    NULL::date,
    NULL,
    NULL,
    'INTEGRANTE',
    TRUE
FROM CLUBE c
WHERE c.PRESIDENTE IS NOT NULL
    AND TRIM(c.PRESIDENTE) != ''
    AND NOT EXISTS (
        SELECT 1 FROM PESSOA p 
        WHERE UPPER(TRIM(p.NOME)) = UPPER(TRIM(c.PRESIDENTE))
    );

-- DIRETOR_TECNICO
INSERT INTO PESSOA (NOME, CPF, RG, DATA_NASCIMENTO, EMAIL, TELEFONE, TIPO, ATIVO)
SELECT DISTINCT
    c.DIRETORTECNICO,
    LPAD(SUBSTRING(MD5(UPPER(c.DIRETORTECNICO)) FROM 1 FOR 11), 11, '0'),
    NULL,
    NULL::date,
    NULL,
    NULL,
    'INTEGRANTE',
    TRUE
FROM CLUBE c
WHERE c.DIRETORTECNICO IS NOT NULL
    AND TRIM(c.DIRETORTECNICO) != ''
    AND NOT EXISTS (
        SELECT 1 FROM PESSOA p 
        WHERE UPPER(TRIM(p.NOME)) = UPPER(TRIM(c.DIRETORTECNICO))
    );

-- INSTRUTOR_CHEFE (RESPONSAVEL)
INSERT INTO PESSOA (NOME, CPF, RG, DATA_NASCIMENTO, EMAIL, TELEFONE, TIPO, ATIVO)
SELECT DISTINCT
    c.RESPONSAVEL,
    LPAD(SUBSTRING(MD5(UPPER(c.RESPONSAVEL)) FROM 1 FOR 11), 11, '0'),
    NULL,
    NULL::date,
    NULL,
    NULL,
    'INTEGRANTE',
    TRUE
FROM CLUBE c
WHERE c.RESPONSAVEL IS NOT NULL
    AND TRIM(c.RESPONSAVEL) != ''
    AND NOT EXISTS (
        SELECT 1 FROM PESSOA p 
        WHERE UPPER(TRIM(p.NOME)) = UPPER(TRIM(c.RESPONSAVEL))
    );

-- ============================================================================
-- PASSO 3: Criar MANDATO_CARGO para cada dirigente com referência ao CARGO
-- ============================================================================

-- PRESIDENTE -> MANDATO_CARGO
INSERT INTO MANDATO_CARGO (
    MANDATO_ID,
    PESSOA_ID,
    CARGO_ID,
    TIPO_OCUPACAO,
    TIPO_VINCULO,
    DATA_INICIO,
    DATA_FIM,
    ATIVO,
    SUBSTITUIU_ID,
    MOTIVO_SAIDA,
    OBSERVACAO
)
SELECT
    m.ID,
    p.ID,
    (SELECT ID FROM CARGO WHERE NOME = 'Presidente' LIMIT 1),
    'TITULAR',
    'ELEITO',
    COALESCE(c.DATAFUNDACAO, '2024-01-01'::date),
    NULL::date,
    TRUE,
    NULL,
    NULL,
    'Migrado de CLUBE.PRESIDENTE'
FROM CLUBE c
INNER JOIN MANDATO m ON m.TIPO_ENTIDADE = 'CLUBE' AND m.ENTIDADE_ID = c.CODIGOCLUBE
INNER JOIN PESSOA p ON UPPER(TRIM(p.NOME)) = UPPER(TRIM(c.PRESIDENTE))
WHERE c.PRESIDENTE IS NOT NULL
    AND TRIM(c.PRESIDENTE) != ''
    AND EXISTS (SELECT 1 FROM CARGO WHERE NOME = 'Presidente')
    AND NOT EXISTS (
        SELECT 1 FROM MANDATO_CARGO mc
        WHERE mc.MANDATO_ID = m.ID
            AND mc.PESSOA_ID = p.ID
            AND mc.OBSERVACAO LIKE '%CLUBE.PRESIDENTE%'
    );

-- DIRETOR_TECNICO -> MANDATO_CARGO
INSERT INTO MANDATO_CARGO (
    MANDATO_ID,
    PESSOA_ID,
    CARGO_ID,
    TIPO_OCUPACAO,
    TIPO_VINCULO,
    DATA_INICIO,
    DATA_FIM,
    ATIVO,
    SUBSTITUIU_ID,
    MOTIVO_SAIDA,
    OBSERVACAO
)
SELECT
    m.ID,
    p.ID,
    (SELECT ID FROM CARGO WHERE NOME = 'Diretor Técnico' LIMIT 1),
    'TITULAR',
    'ELEITO',
    COALESCE(c.DATAFUNDACAO, '2024-01-01'::date),
    NULL::date,
    TRUE,
    NULL,
    NULL,
    'Migrado de CLUBE.DIRETORTECNICO'
FROM CLUBE c
INNER JOIN MANDATO m ON m.TIPO_ENTIDADE = 'CLUBE' AND m.ENTIDADE_ID = c.CODIGOCLUBE
INNER JOIN PESSOA p ON UPPER(TRIM(p.NOME)) = UPPER(TRIM(c.DIRETORTECNICO))
WHERE c.DIRETORTECNICO IS NOT NULL
    AND TRIM(c.DIRETORTECNICO) != ''
    AND EXISTS (SELECT 1 FROM CARGO WHERE NOME = 'Diretor Técnico')
    AND NOT EXISTS (
        SELECT 1 FROM MANDATO_CARGO mc
        WHERE mc.MANDATO_ID = m.ID
            AND mc.PESSOA_ID = p.ID
            AND mc.OBSERVACAO LIKE '%CLUBE.DIRETORTECNICO%'
    );

-- INSTRUTOR_CHEFE (RESPONSAVEL) -> MANDATO_CARGO
INSERT INTO MANDATO_CARGO (
    MANDATO_ID,
    PESSOA_ID,
    CARGO_ID,
    TIPO_OCUPACAO,
    TIPO_VINCULO,
    DATA_INICIO,
    DATA_FIM,
    ATIVO,
    SUBSTITUIU_ID,
    MOTIVO_SAIDA,
    OBSERVACAO
)
SELECT
    m.ID,
    p.ID,
    (SELECT ID FROM CARGO WHERE NOME = 'Instrutor Chefe' LIMIT 1),
    'TITULAR',
    'ELEITO',
    COALESCE(c.DATAFUNDACAO, '2024-01-01'::date),
    NULL::date,
    TRUE,
    NULL,
    NULL,
    'Migrado de CLUBE.RESPONSAVEL'
FROM CLUBE c
INNER JOIN MANDATO m ON m.TIPO_ENTIDADE = 'CLUBE' AND m.ENTIDADE_ID = c.CODIGOCLUBE
INNER JOIN PESSOA p ON UPPER(TRIM(p.NOME)) = UPPER(TRIM(c.RESPONSAVEL))
WHERE c.RESPONSAVEL IS NOT NULL
    AND TRIM(c.RESPONSAVEL) != ''
    AND EXISTS (SELECT 1 FROM CARGO WHERE NOME = 'Instrutor Chefe')
    AND NOT EXISTS (
        SELECT 1 FROM MANDATO_CARGO mc
        WHERE mc.MANDATO_ID = m.ID
            AND mc.PESSOA_ID = p.ID
            AND mc.OBSERVACAO LIKE '%CLUBE.RESPONSAVEL%'
    );

-- ============================================================================
-- PASSO 4: Validação - Listar mandatos criados e seus cargos
-- ============================================================================
SELECT 
    m.ID as mandato_id,
    m.TIPO_ENTIDADE,
    c.NOMECLUBE as clube,
    m.DATA_INICIO,
    m.DATA_FIM,
    m.ATIVO,
    COUNT(mc.ID) as total_cargos,
    STRING_AGG(
        p.NOME || ' - ' || cg.NOME || ' (' || mc.TIPO_OCUPACAO || ')', 
        ', ' 
        ORDER BY cg.HIERARQUIA
    ) as ocupantes
FROM MANDATO m
INNER JOIN CLUBE c ON c.CODIGOCLUBE = m.ENTIDADE_ID AND m.TIPO_ENTIDADE = 'CLUBE'
LEFT JOIN MANDATO_CARGO mc ON mc.MANDATO_ID = m.ID
LEFT JOIN PESSOA p ON p.ID = mc.PESSOA_ID
LEFT JOIN CARGO cg ON cg.ID = mc.CARGO_ID
GROUP BY m.ID, m.TIPO_ENTIDADE, c.NOMECLUBE, m.DATA_INICIO, m.DATA_FIM, m.ATIVO
ORDER BY m.ID DESC;
