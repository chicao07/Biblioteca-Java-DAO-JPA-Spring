-- =========================================
-- Banco de dados de PRODUÇÃO
-- =========================================
CREATE DATABASE IF NOT EXISTS biblioteca_db;
USE biblioteca_db;

CREATE TABLE IF NOT EXISTS autor (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    nacionalidade VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS livro (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(150) NOT NULL,
    preco DOUBLE NOT NULL,
    ano_publicacao INT NOT NULL,
    autor_id INT,
    FOREIGN KEY (autor_id) REFERENCES autor(id)
);

-- =========================================
-- Banco de dados de TESTES (JUnit)
-- =========================================
CREATE DATABASE IF NOT EXISTS biblioteca_test_db;
USE biblioteca_test_db;

CREATE TABLE IF NOT EXISTS autor (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    nacionalidade VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS livro (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(150) NOT NULL,
    preco DOUBLE NOT NULL,
    ano_publicacao INT NOT NULL,
    autor_id INT,
    FOREIGN KEY (autor_id) REFERENCES autor(id)
);