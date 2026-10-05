package negocio.modelo.dao.implementaciones;

import negocio.modelo.dao.interfaces.DistanciaDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DistanciaDAOImpl implements DistanciaDAO {

    @Override
    public int obtenerKm(Connection con, int idOrigen, int idDestino) throws SQLException {
        String sql = "SELECT km FROM distancias WHERE id_origen = ? AND id_destino = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idOrigen);
            ps.setInt(2, idDestino);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt("km") : 0;
            }
        }
    }
}
