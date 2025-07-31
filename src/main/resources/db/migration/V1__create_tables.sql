CREATE TABLE usuarios (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    version BIGINT,
    CONSTRAINT usuarios_email_unique UNIQUE (email)
);

CREATE TABLE pedidos (
    id BIGSERIAL PRIMARY KEY,
    descripcion VARCHAR(255) NOT NULL,
    usuario_id BIGINT REFERENCES usuarios(id),
    version BIGINT
);
