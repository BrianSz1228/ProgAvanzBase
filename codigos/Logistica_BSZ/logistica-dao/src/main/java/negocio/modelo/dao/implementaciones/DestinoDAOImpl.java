package negocio.modelo.dao.implementaciones;

import negocio.modelo.dao.interfaces.DestinoDAO;
import negocio.modelo.dominio.Destino;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DestinoDAOImpl implements DestinoDAO {

    @Override
    public List<Destino> listarTodos(Connection con) throws SQLException {
        List<Destino> destinos = new ArrayList<>();
        String sql = "SELECT id_destino, nombre FROM destinos ORDER BY nombre";

        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                destinos.add(mapear(rs));
            }
        }
        return destinos;
    }

    @Override
    public Destino buscarPorId(Connection con, int idDestino) throws SQLException {
        String sql = "SELECT id_destino, nombre FROM destinos WHERE id_destino = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idDestino);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }

    private Destino mapear(ResultSet rs) throws SQLException {
        return new Destino(rs.getInt("id_destino"), rs.getString("nombre"));
    }
}
