-- Crear base de datos si no existe
IF DB_ID('biblioteca') IS NULL
    CREATE DATABASE biblioteca;
GO

USE biblioteca;
GO

-- Eliminar tablas si existen
IF OBJECT_ID('prestamos', 'U') IS NOT NULL
    DROP TABLE prestamos;
GO

IF OBJECT_ID('libros', 'U') IS NOT NULL
    DROP TABLE libros;
GO

-- Crear tabla libros
CREATE TABLE libros (
    id INT IDENTITY(1,1) PRIMARY KEY,
    titulo VARCHAR(150) NOT NULL,
    autor VARCHAR(100) NOT NULL,
    stock INT NOT NULL
);
GO

-- Crear tabla prestamos
CREATE TABLE prestamos (
    id INT IDENTITY(1,1) PRIMARY KEY,
    libro_id INT,
    usuario VARCHAR(100),
    fecha_prestamo DATE,
    CONSTRAINT FK_prestamos_libros
        FOREIGN KEY (libro_id) REFERENCES libros(id)
);
GO

-- Carga inicial de datos
INSERT INTO libros (titulo, autor, stock)
VALUES
('Clean Code', 'Robert C. Martin', 5),
('Design Patterns', 'Erich Gamma', 2),
('Java: The Complete Reference', 'Herbert Schildt', 0),
('Effective Java', 'Joshua Bloch', 3);
GO

-- Procedimiento almacenado
CREATE PROCEDURE sp_realizar_prestamo
    @p_libro_id INT,
    @p_usuario VARCHAR(100),
    @p_codigo_respuesta VARCHAR(150) OUTPUT
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @v_stock INT;

    SELECT @v_stock = stock
    FROM libros
    WHERE id = @p_libro_id;

    IF @v_stock IS NULL
    BEGIN
        SET @p_codigo_respuesta = 'Libro no existe';
    END
    ELSE IF @v_stock > 0
    BEGIN
        -- Restar stock
        UPDATE libros
        SET stock = stock - 1
        WHERE id = @p_libro_id;

        -- Registrar préstamo
        INSERT INTO prestamos (libro_id, usuario, fecha_prestamo)
        VALUES (@p_libro_id, @p_usuario, CAST(GETDATE() AS DATE));

        SET @p_codigo_respuesta = 'Operacion realizada con exito';
    END
    ELSE
    BEGIN
        SET @p_codigo_respuesta = 'Sin stock';
    END
END;
GO