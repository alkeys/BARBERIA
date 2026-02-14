-- =========================
-- CREAR BASE DE DATOS
-- =========================
CREATE DATABASE barberia_db;

-- Luego conéctate a la base:
-- \c barberia_db;

-- =========================
-- TABLA ROLES
-- =========================
CREATE TABLE roles (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL UNIQUE,
    descripcion TEXT
);

CREATE INDEX idx_nombre ON roles (nombre);

CREATE INDEX idx_descripcion ON roles (descripcion);

-- =========================
-- TABLA USUARIOS
-- =========================
CREATE TABLE usuarios (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    role_id BIGINT,
    nombre_completo VARCHAR(255),
    usuario VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    activo BOOLEAN DEFAULT TRUE NOT NULL,
    fecha_creacion TIMESTAMP,
    CONSTRAINT fk_usuario_role FOREIGN KEY (role_id) REFERENCES roles (id)
);

CREATE INDEX idx_usuario ON usuarios (usuario);

CREATE INDEX idx_nombre_completo ON usuarios (nombre_completo);

CREATE INDEX idx_activo ON usuarios (activo);

CREATE INDEX idx_fecha_creacion ON usuarios (fecha_creacion);

CREATE INDEX idx_usuario_activo ON usuarios (usuario, password_hash);

-- =========================
-- TABLA CLIENTES
-- =========================
CREATE TABLE clientes (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    email VARCHAR(100),
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =========================
-- TABLA BARBEROS
-- =========================
CREATE TABLE barberos (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    telefono VARCHAR(20),
    activo BOOLEAN DEFAULT TRUE,
    disponible BOOLEAN DEFAULT TRUE,
    id_usuario BIGINT,
    CONSTRAINT fk_barbero_usuario FOREIGN KEY (id_usuario) REFERENCES usuarios (id)
);

-- =========================
-- TABLA SERVICIOS
-- =========================
CREATE TABLE servicios (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(255),
    precio NUMERIC(10, 2) NOT NULL,
    duracion_minutos INT NOT NULL
);

-- =========================
-- TABLA CITAS
-- =========================
CREATE TABLE citas (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    cliente_id BIGINT NOT NULL,
    barbero_id BIGINT NOT NULL,
    servicio_id BIGINT NOT NULL,
    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    estado VARCHAR(30) DEFAULT 'PENDIENTE',
    observaciones VARCHAR(255),
    CONSTRAINT fk_cliente FOREIGN KEY (cliente_id) REFERENCES clientes (id),
    CONSTRAINT fk_barbero FOREIGN KEY (barbero_id) REFERENCES barberos (id),
    CONSTRAINT fk_servicio FOREIGN KEY (servicio_id) REFERENCES servicios (id)
);