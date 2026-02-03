-- ============================================================================
-- REFATORAÇÃO: COMPOSICAO_MANDATO + OCUPACAO_CARGO → MANDATO_CARGO
-- ============================================================================
-- Este script consolida as tabelas COMPOSICAO_MANDATO e OCUPACAO_CARGO em uma
-- única tabela MANDATO_CARGO, simplificando o modelo e alinhando com a entidade Java.
--
-- Relacionamento com CLUBE: Não há FK direta. O relacionamento é feito via
-- MANDATO.TIPO_ENTIDADE='CLUBE' e MANDATO.ENTIDADE_ID=CLUBE.CODIGOCLUBE
-- ============================================================================

-- ============================================================================
-- PASSO 1: Criar a nova tabela MANDATO_CARGO
-- ============================================================================
CREATE TABLE IF NOT EXISTS MANDATO_CARGO (
    ID BIGSERIAL PRIMARY KEY,
    MANDATO_ID BIGINT NOT NULL REFERENCES MANDATO(ID) ON DELETE CASCADE,
    PESSOA_ID BIGINT NOT NULL REFERENCES PESSOA(ID),
    CARGO_ID BIGINT NOT NULL REFERENCES CARGO(ID),
    TIPO_OCUPACAO VARCHAR(20) NOT NULL,
    TIPO_VINCULO VARCHAR(20) NOT NULL,
    DATA_INICIO DATE NOT NULL,
    DATA_FIM DATE,
    ATIVO BOOLEAN DEFAULT TRUE,
    SUBSTITUIU_ID BIGINT REFERENCES MANDATO_CARGO(ID),
    MOTIVO_SAIDA VARCHAR(30),
    OBSERVACAO TEXT
);

CREATE INDEX IF NOT EXISTS idx_mandato_cargo_mandato ON MANDATO_CARGO(MANDATO_ID);
CREATE INDEX IF NOT EXISTS idx_mandato_cargo_pessoa ON MANDATO_CARGO(PESSOA_ID);
CREATE INDEX IF NOT EXISTS idx_mandato_cargo_cargo ON MANDATO_CARGO(CARGO_ID);
CREATE INDEX IF NOT EXISTS idx_mandato_cargo_ativo ON MANDATO_CARGO(ATIVO);
CREATE INDEX IF NOT EXISTS idx_mandato_cargo_tipo ON MANDATO_CARGO(TIPO_OCUPACAO);

-- ============================================================================
-- PASSO 2: Migrar dados de OCUPACAO_CARGO → MANDATO_CARGO (se existir)
-- ============================================================================
DO $$
BEGIN
    IF EXISTS (SELECT FROM information_schema.tables WHERE table_name = 'ocupacao_cargo') THEN
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
            cm.MANDATO_ID,
            oc.PESSOA_ID,
            cm.CARGO_ID,
            oc.TIPO_OCUPACAO,
            oc.TIPO_VINCULO,
            oc.DATA_INICIO,
            oc.DATA_FIM,
            oc.ATIVO,
            oc.SUBSTITUIU_ID,
            oc.MOTIVO_SAIDA,
            oc.OBSERVACAO
        FROM OCUPACAO_CARGO oc
        INNER JOIN COMPOSICAO_MANDATO cm ON cm.ID = oc.COMPOSICAO_MANDATO_ID
        WHERE NOT EXISTS (
            SELECT 1 FROM MANDATO_CARGO mc
            WHERE mc.MANDATO_ID = cm.MANDATO_ID
                AND mc.PESSOA_ID = oc.PESSOA_ID
                AND mc.CARGO_ID = cm.CARGO_ID
        );
        
        RAISE NOTICE 'Dados migrados de OCUPACAO_CARGO para MANDATO_CARGO';
    ELSE
        RAISE NOTICE 'Tabela OCUPACAO_CARGO não existe. Pulando migração.';
    END IF;
END $$;

-- ============================================================================
-- PASSO 3: Remover as tabelas antigas (co (se tabelas antigas existirem)
-- ============================================================================
DO $$
BEGIN
    IF EXISTS (SELECT FROM information_schema.tables WHERE table_name = 'ocupacao_cargo') THEN
        RAISE NOTICE 'Comparando contagens:';
        RAISE NOTICE 'OCUPACAO_CARGO: %', (SELECT COUNT(*) FROM OCUPACAO_CARGO);
        RAISE NOTICE 'MANDATO_CARGO: %', (SELECT COUNT(*) FROM MANDATO_CARGO);
    ELSE
        RAISE NOTICE 'Validação: Tabela MANDATO_CARGO possui % registros', (SELECT COUNT(*) FROM MANDATO_CARGO);
    END IF;
END $$;

-- Listar alguns registros da nova estrutura
    COUNT(*) as total
FROM OCUPACAO_CARGO
UNION ALL
SELECT 
    'MANDATO_CARGO' as tabela,
    COUNT(*) as total
FROM MANDATO_CARGO;

-- Listar alguns registros migrados
SELECT 
    mc.ID,
    m.TIPO_ENTIDADE,
    m.ENTIDADE_ID,
    c.NOME as cargo,
    p.NOME as pessoa,
    mc.TIPO_OCUPACAO,
    mc.TIPO_VINCULO,
    mc.DATA_INICIO,
    mc.ATIVO
FROM MANDATO_CARGO mc
INNER JOIN MANDATO m ON m.ID = mc.MANDATO_ID
INNER JOIN CARGO c ON c.ID = mc.CARGO_ID
INNER JOIN PESSOA p ON p.ID = mc.PESSOA_ID
ORDER BY mc.ID
LIMIT 10;
