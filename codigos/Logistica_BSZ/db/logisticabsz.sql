--  Parcial 2do cuatrimestre - Logistica - Brian szestaluk
--  Script de creacion de BD, datos de ejemplo y stored procedure
DROP DATABASE IF EXISTS logisticabsz;
CREATE DATABASE logisticabsz CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE logisticabsz;

--  TABLAS

CREATE TABLE categorias (
    id_categoria      INT AUTO_INCREMENT PRIMARY KEY,
    nombre            VARCHAR(5) NOT NULL UNIQUE,
    toneladas_maximas INT        NOT NULL
);

CREATE TABLE destinos (
    id_destino INT AUTO_INCREMENT PRIMARY KEY,
    nombre     VARCHAR(50) NOT NULL UNIQUE
);

-- Matriz de distancias (se guardan ambos sentidos: A->B y B->A)
CREATE TABLE distancias (
    id_origen  INT NOT NULL,
    id_destino INT NOT NULL,
    km         INT NOT NULL,
    PRIMARY KEY (id_origen, id_destino),
    FOREIGN KEY (id_origen)  REFERENCES destinos (id_destino),
    FOREIGN KEY (id_destino) REFERENCES destinos (id_destino)
);

CREATE TABLE usuarios (
    id_usuario     INT AUTO_INCREMENT PRIMARY KEY,
    nombre_usuario VARCHAR(50)  NOT NULL UNIQUE,
    password       VARCHAR(100) NOT NULL,
    perfil         ENUM('ADMIN', 'CHOFER') NOT NULL
);

CREATE TABLE choferes (
    id_chofer        INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario       INT         NOT NULL UNIQUE,
    id_categoria     INT         NOT NULL,
    nombre           VARCHAR(50) NOT NULL,
    apellido         VARCHAR(50) NOT NULL,
    dni              VARCHAR(15) NOT NULL UNIQUE,
    fecha_nacimiento DATE        NOT NULL,
    telefono_celular VARCHAR(20) NOT NULL,
    FOREIGN KEY (id_usuario)   REFERENCES usuarios (id_usuario),
    FOREIGN KEY (id_categoria) REFERENCES categorias (id_categoria)
);

CREATE TABLE camiones (
    id_camion         INT AUTO_INCREMENT PRIMARY KEY,
    marca             VARCHAR(50)  NOT NULL,
    modelo            VARCHAR(50)  NOT NULL,
    dominio           VARCHAR(10)  NOT NULL UNIQUE,
    toneladas_maximas DECIMAL(5,2) NOT NULL,
    litros_tanque     DECIMAL(6,2) NOT NULL,
    consumo_litros_km DECIMAL(5,2) NOT NULL
);

-- Relacion N:M: choferes autorizados a manejar camiones
CREATE TABLE chofer_camion (
    id_chofer INT NOT NULL,
    id_camion INT NOT NULL,
    PRIMARY KEY (id_chofer, id_camion),
    FOREIGN KEY (id_chofer) REFERENCES choferes (id_chofer) ON DELETE CASCADE,
    FOREIGN KEY (id_camion) REFERENCES camiones (id_camion) ON DELETE CASCADE
);

CREATE TABLE viajes (
    id_viaje     INT AUTO_INCREMENT PRIMARY KEY,
    id_chofer    INT NOT NULL,
    id_camion    INT NOT NULL,
    id_origen    INT NOT NULL,
    id_destino   INT NOT NULL,
    km           INT NOT NULL,
    dias         INT NOT NULL,
    tanques      INT NOT NULL,
    estado       ENUM('ASIGNADO', 'EN_CURSO', 'FINALIZADO') NOT NULL DEFAULT 'ASIGNADO',
    fecha_carga  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_inicio DATETIME NULL,
    fecha_fin    DATETIME NULL,
    FOREIGN KEY (id_chofer)  REFERENCES choferes (id_chofer),
    FOREIGN KEY (id_camion)  REFERENCES camiones (id_camion),
    FOREIGN KEY (id_origen)  REFERENCES destinos (id_destino),
    FOREIGN KEY (id_destino) REFERENCES destinos (id_destino)
);

-- 
--  DATOS DE EJEMPLO
-- 
INSERT INTO categorias (nombre, toneladas_maximas) VALUES
    ('C1', 5), ('C2', 10), ('E1', 15), ('E2', 20);

-- Los ids quedan: 1 CABA, 2 Cordoba, 3 Corrientes, 4 Formosa,
-- 5 La Plata, 6 La Rioja, 7 Mendoza, 8 Neuquen
INSERT INTO destinos (nombre) VALUES
    ('CABA'), ('Córdoba'), ('Corrientes'), ('Formosa'),
    ('La Plata'), ('La Rioja'), ('Mendoza'), ('Neuquén');

-- Se cargan los 28 pares en un sentido
INSERT INTO distancias (id_origen, id_destino, km) VALUES
    (1,2,646), (1,3,792), (1,4,933), (1,5,53),   (1,6,986),  (1,7,985),  (1,8,989),
    (2,3,677), (2,4,824), (2,5,698), (2,6,340),  (2,7,466),  (2,8,907),
    (3,4,157), (3,5,830), (3,6,814), (3,7,1131), (3,8,1534),
    (4,5,968), (4,6,927), (4,7,1269), (4,8,1690),
    (5,6,1038), (5,7,1029), (5,8,1005),
    (6,7,427), (6,8,1063),
    (7,8,676);
-- y se espejan para el otro sentido (total 56 filas)
INSERT INTO distancias (id_origen, id_destino, km)
    SELECT id_destino, id_origen, km FROM distancias;

-- Usuarios (password en texto)
-- Se cargan con el id para que coincidan con los de choferes
INSERT INTO usuarios (id_usuario, nombre_usuario, password, perfil) VALUES
    (1,  'admin',      'admin123', 'ADMIN'),
    (2,  'jperez',     '1234',     'CHOFER'),
    (3,  'mgomez',     '1234',     'CHOFER'),
    (4,  'lrodriguez', '1234',     'CHOFER'),
    (15, 'bri',        'bri',      'CHOFER');

INSERT INTO choferes (id_chofer, id_usuario, id_categoria, nombre, apellido, dni, fecha_nacimiento, telefono_celular) VALUES
    (1, 2,  1, 'Juan',  'Pérez',     '30111222', '1985-03-12', '1155551111'),
    (2, 3,  2, 'María', 'Gómez',     '32333444', '1990-07-25', '1155552222'),
    (3, 4,  4, 'Luis',  'Rodríguez', '28555666', '1980-11-03', '1155553333'),
    (8, 15, 4, 'Brian', 'sz',        '46',       '2000-01-01', '111');

INSERT INTO camiones (id_camion, marca, modelo, dominio, toneladas_maximas, litros_tanque, consumo_litros_km) VALUES
    (1,  'Iveco',         'Daily 55C',  'AB123CD',      5.00, 120.00,  0.20),
    (2,  'Mercedes-Benz', 'Atego 1722', 'AC456EF',     10.00, 250.00,  0.30),
    (3,  'Scania',        'P310',       'AD789GH',     15.00, 400.00,  0.35),
    (4,  'Volvo',         'FH 460',     'AE012IJ',     20.00, 600.00,  0.40),
    (5,  'aaa',           'aaa',        'aaa',          1.00,   1.00,  1.00),
    (6,  'aaaa',          '1234',       '12-aaa-123', 100.00, 100.00, 20.00),
    (8,  'abc',           'abc',        'abc',         10.00,  10.00, 10.00),
    (24, 'CAMIOn',        'NOse',       '11aaa11',     19.00, 201.00,  0.35);

INSERT INTO chofer_camion (id_chofer, id_camion) VALUES
    (1,1),
    (2,1), (2,2),
    (3,2), (3,3), (3,4),
    (8,1), (8,2), (8,3), (8,4), (8,5), (8,8), (8,24);

-- Viajes cargados (finalizados, asignados y uno en curso)
INSERT INTO viajes (id_viaje, id_chofer, id_camion, id_origen, id_destino, km, dias, tanques, estado, fecha_carga, fecha_inicio, fecha_fin) VALUES
    (1, 2, 2,  1, 2, 646, 4, 1, 'FINALIZADO', '2026-10-04 17:48:25', '2026-10-04 17:48:25', '2026-10-04 17:48:26'),
    (2, 2, 2,  1, 2, 646, 4, 1, 'FINALIZADO', '2026-10-04 18:06:09', '2026-10-04 18:06:10', '2026-10-04 18:06:10'),
    (3, 2, 1,  2, 8, 907, 5, 2, 'FINALIZADO', '2026-10-04 22:46:22', '2026-10-04 23:20:12', '2026-10-04 23:20:18'),
    (4, 3, 4,  1, 8, 989, 5, 1, 'ASIGNADO',   '2026-10-06 19:22:57', NULL, NULL),
    (5, 3, 2,  1, 8, 989, 5, 2, 'ASIGNADO',   '2026-10-06 19:23:27', NULL, NULL),
    (6, 8, 24, 1, 4, 933, 5, 2, 'EN_CURSO',   '2026-10-06 21:52:24', '2026-10-06 21:58:58', NULL);

--  STORED PROCEDURE (se invoca con CallableStatement)
--  Valida que el chofer pueda manejar el camion y que este no tenga un
--  viaje sin finalizar. Devuelve el resultado por el parametro OUT.
DELIMITER //

CREATE PROCEDURE sp_validar_asignacion(
    IN  p_id_chofer INT,
    IN  p_id_camion INT,
    OUT p_resultado VARCHAR(100)
)
BEGIN
    DECLARE v_autorizado INT DEFAULT 0;
    DECLARE v_en_viaje   INT DEFAULT 0;

    SELECT COUNT(*) INTO v_autorizado
    FROM chofer_camion
    WHERE id_chofer = p_id_chofer AND id_camion = p_id_camion;

    SELECT COUNT(*) INTO v_en_viaje
    FROM viajes
    WHERE id_camion = p_id_camion AND estado <> 'FINALIZADO';

    IF v_autorizado = 0 THEN
        SET p_resultado = 'ERROR: El chofer no esta autorizado para ese camion.';
    ELSEIF v_en_viaje > 0 THEN
        SET p_resultado = 'ERROR: El camion ya tiene un viaje asignado.';
    ELSE
        SET p_resultado = 'OK: Asignacion valida.';
    END IF;
END //

DELIMITER ;
