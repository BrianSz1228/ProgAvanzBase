package negocio.util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class JdbcUtil {

    private JdbcUtil() {
    }

    // Devuelve el id que genero la base en el ultimo insert
    public static int obtenerIdGenerado(PreparedStatement ps) throws SQLException {
        try (ResultSet rs = ps.getGeneratedKeys()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        throw new SQLException("No se pudo obtener el id generado");
    }

    // Las fechas pueden venir null desde la base
    public static LocalDateTime aLocalDateTime(Timestamp timestamp) {
        return timestamp == null ? null : timestamp.toLocalDateTime();
    }
}
