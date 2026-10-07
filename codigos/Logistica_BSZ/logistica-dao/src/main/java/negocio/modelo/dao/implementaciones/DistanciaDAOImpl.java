package negocio.modelo.dao.implementaciones;

import negocio.modelo.dao.interfaces.DistanciaDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DistanciaDAOImpl implements DistanciaDAO {

    // Busca los km en la tabla de distancias, devuelve 0 si no hay dato
    @Override
    public int obtenerKm(Connection con, int idOrigen, int idDestino) throws SQLException {
        String sql = "SELECT km FROM distancias WHERE id_origen = ? AND id_destino = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idOrigen);
            ps.setInt(2, idDestino);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("km");
                }
            }
        }
        return 0;
    }
}
