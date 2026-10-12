-- ========================================
-- SEQUENCES
-- ========================================

CREATE SEQUENCE TBL_USUARIOS_SEQ
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;

CREATE SEQUENCE TBL_SETORES_SEQ
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;

CREATE SEQUENCE TBL_FUNCIONARIOS_SEQ
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;

-- ========================================
-- TABELA: USUARIOS
-- ========================================

CREATE TABLE tbl_usuarios (
    usuario_id NUMBER PRIMARY KEY,
    nome VARCHAR2(100) NOT NULL,
    email VARCHAR2(150) NOT NULL,
    senha VARCHAR2(255) NOT NULL,
    role VARCHAR2(50) NOT NULL,

    CONSTRAINT email_unico UNIQUE (email)
);

-- ========================================
-- TABELA: SETORES
-- ========================================

CREATE TABLE tbl_setores (
    id NUMBER PRIMARY KEY,
    nome VARCHAR2(100) NOT NULL,
    meta_reducao_percentual NUMBER NOT NULL,
    responsavel VARCHAR2(100) NOT NULL
);

-- ========================================
-- TABELA: FUNCIONARIOS
-- ========================================

CREATE TABLE tbl_funcionarios (
    id NUMBER PRIMARY KEY,
    nome VARCHAR2(100) NOT NULL,
    cargo VARCHAR2(100) NOT NULL,
    salario NUMBER NOT NULL,
    ativo VARCHAR2(10) NOT NULL,

    setor_id NUMBER NOT NULL,

    CONSTRAINT fk_funcionario_setor
        FOREIGN KEY (setor_id)
        REFERENCES tbl_setores(id)
);