CREATE DATABASE biblioteca;

\c biblioteca

DROP TABLE IF EXISTS prestamos;
DROP TABLE IF EXISTS libros;

CREATE TABLE libros (
    id SERIAL PRIMARY KEY,
    titulo VARCHAR(150) NOT NULL,
    autor VARCHAR(100) NOT NULL,
    stock INT NOT NULL
);

CREATE TABLE prestamos (
    id SERIAL PRIMARY KEY,
    libro_id INT REFERENCES libros(id),
    usuario VARCHAR(100),
    fecha_prestamo DATE
);

INSERT INTO libros (titulo, autor, stock) VALUES
('Clean Code', 'Robert C. Martin', 5),
('Design Patterns', 'Erich Gamma', 2),
('Java: The Complete Reference', 'Herbert Schildt', 0),
('Effective Java', 'Joshua Bloch', 3);

CREATE OR REPLACE PROCEDURE sp_realizar_prestamo(
    IN p_libro_id INT,
    IN p_usuario VARCHAR(100),
    INOUT p_codigo_respuesta VARCHAR(150)
)
LANGUAGE plpgsql
AS $$
DECLARE
    v_stock INT;
BEGIN
    SELECT stock
    INTO v_stock
    FROM libros
    WHERE id = p_libro_id;

    IF NOT FOUND THEN
        p_codigo_respuesta := 'Libro no existe';

    ELSIF v_stock > 0 THEN
        -- Restar stock
        UPDATE libros
        SET stock = stock - 1
        WHERE id = p_libro_id;

        -- Registrar préstamo
        INSERT INTO prestamos (libro_id, usuario, fecha_prestamo)
        VALUES (p_libro_id, p_usuario, CURRENT_DATE);

        p_codigo_respuesta := 'Operacion realizada con exito';

    ELSE
        p_codigo_respuesta := 'Sin stock';
    END IF;
END;
$$;