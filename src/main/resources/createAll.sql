CREATE TABLE IF NOT EXISTS party (
    id int auto_increment NOT NULL UNIQUE,
    nombre VARCHAR(255) NOT NULL UNIQUE,
    numeroDeAventureros int NOT NULL,
    PRIMARY KEY (id)
    );