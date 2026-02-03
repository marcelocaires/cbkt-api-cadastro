-- ============================================================================
-- CRIAÇÃO DAS TABELAS PARA O SISTEMA DE MANDATOS
-- ============================================================================

-- ============================================================================
-- TABELA: PESSOA
-- Entidade base para atletas, dirigentes e colaboradores
-- ============================================================================
CREATE TABLE IF NOT EXISTS PESSOA (
    ID BIGSERIAL PRIMARY KEY,
    NOME VARCHAR(120) NOT NULL,
    CPF VARCHAR(11) UNIQUE NOT NULL,
    RG VARCHAR(20),
    DATA_NASCIMENTO DATE,
    EMAIL VARCHAR(100),
    TELEFONE VARCHAR(20),
    TIPO VARCHAR(20) NOT NULL,
    ATIVO BOOLEAN DEFAULT TRUE,
    CRIADO_EM TIMESTAMP DEFAULT NOW(),
    ATUALIZADO_EM TIMESTAMP DEFAULT NOW()
);

CREATE INDEX idx_pessoa_cpf ON PESSOA(CPF);
CREATE INDEX idx_pessoa_nome ON PESSOA(NOME);

-- ============================================================================
-- TABELA: CARGO
-- Tabela de domínio com tipos de cargos disponíveis (genéricos, sem distinção de entidade)
-- ============================================================================
CREATE TABLE IF NOT EXISTS CARGO (
    ID BIGSERIAL PRIMARY KEY,
    NOME VARCHAR(100) NOT NULL UNIQUE,
    HIERARQUIA INTEGER,
    REQUER_SUPLENTE BOOLEAN,
    ATIVO BOOLEAN DEFAULT TRUE,
    CRIADO_EM TIMESTAMP DEFAULT NOW(),
    ATUALIZADO_EM TIMESTAMP DEFAULT NOW()
);

CREATE INDEX idx_cargo_nome ON CARGO(NOME);
CREATE INDEX idx_cargo_ativo ON CARGO(ATIVO);

-- ============================================================================
-- TABELA: MANDATO
-- Período de gestão de uma entidade (Clube/Federação/Confederação)
-- ============================================================================
CREATE TABLE IF NOT EXISTS MANDATO (
    ID BIGSERIAL PRIMARY KEY,
    TIPO_ENTIDADE VARCHAR(20) NOT NULL,
    ENTIDADE_ID BIGINT NOT NULL,
    DATA_INICIO DATE NOT NULL,
    DATA_FIM DATE,
    ATIVO BOOLEAN DEFAULT TRUE,
    DESCRICAO VARCHAR(100),
    DOCUMENTO_PATH VARCHAR(500),
    CRIADO_EM TIMESTAMP NOT NULL DEFAULT NOW(),
    ATUALIZADO_EM TIMESTAMP,
    CRIADO_POR_CPF VARCHAR(11) NOT NULL,
    CRIADO_POR_NOME VARCHAR(120) NOT NULL,
    ATUALIZADO_POR_CPF VARCHAR(11),
    ATUALIZADO_POR_NOME VARCHAR(120),
    VERSAO BIGINT DEFAULT 0
);

CREATE INDEX idx_mandato_entidade ON MANDATO(TIPO_ENTIDADE, ENTIDADE_ID);
CREATE INDEX idx_mandato_ativo ON MANDATO(ATIVO);
CREATE INDEX idx_mandato_criado_em ON MANDATO(CRIADO_EM);

-- ============================================================================
-- TABELA: COMPOSICAO_MANDATO
-- Define quais cargos compõem um mandato específico
-- ============================================================================
CREATE TABLE IF NOT EXISTS COMPOSICAO_MANDATO (
    ID BIGSERIAL PRIMARY KEY,
    MANDATO_ID BIGINT NOT NULL REFERENCES MANDATO(ID) ON DELETE CASCADE,
    CARGO_ID BIGINT NOT NULL REFERENCES CARGO(ID),
    OBRIGATORIO BOOLEAN DEFAULT FALSE,
    PERMITE_SUPLENTE BOOLEAN DEFAULT FALSE,
    ORDEM INTEGER,
    CRIADO_EM TIMESTAMP DEFAULT NOW(),
    ATUALIZADO_EM TIMESTAMP
);

CREATE INDEX idx_composicao_mandato ON COMPOSICAO_MANDATO(MANDATO_ID);
CREATE INDEX idx_composicao_cargo ON COMPOSICAO_MANDATO(CARGO_ID);
CREATE UNIQUE INDEX idx_composicao_mandato_cargo ON COMPOSICAO_MANDATO(MANDATO_ID, CARGO_ID);

-- ============================================================================
-- TABELA: OCUPACAO_CARGO
-- Quem ocupa cada cargo (com histórico de substituições)
-- ============================================================================
CREATE TABLE IF NOT EXISTS OCUPACAO_CARGO (
    ID BIGSERIAL PRIMARY KEY,
    COMPOSICAO_MANDATO_ID BIGINT NOT NULL REFERENCES COMPOSICAO_MANDATO(ID) ON DELETE CASCADE,
    PESSOA_ID BIGINT NOT NULL REFERENCES PESSOA(ID),
    TIPO_OCUPACAO VARCHAR(20) NOT NULL,
    TIPO_VINCULO VARCHAR(20) NOT NULL,
    DATA_INICIO DATE NOT NULL,
    DATA_FIM DATE,
    ATIVO BOOLEAN DEFAULT TRUE,
    SUBSTITUIU_ID BIGINT REFERENCES OCUPACAO_CARGO(ID),
    MOTIVO_SAIDA VARCHAR(30),
    OBSERVACAO TEXT,
    CRIADO_EM TIMESTAMP DEFAULT NOW(),
    ATUALIZADO_EM TIMESTAMP
);

CREATE INDEX idx_ocupacao_composicao ON OCUPACAO_CARGO(COMPOSICAO_MANDATO_ID);
CREATE INDEX idx_ocupacao_pessoa ON OCUPACAO_CARGO(PESSOA_ID);
CREATE INDEX idx_ocupacao_ativo ON OCUPACAO_CARGO(ATIVO);
CREATE INDEX idx_ocupacao_tipo ON OCUPACAO_CARGO(TIPO_OCUPACAO);
