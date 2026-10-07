package negocio.modelo.dao.implementaciones;

import negocio.modelo.dao.interfaces.CategoriaDAO;
import negocio.modelo.dominio.Categoria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAOImpl implements CategoriaDAO {

    @Override
    public List<Categoria> listarTodas(Connection con) throws SQLException {
        List<Categoria> categorias = new ArrayList<>();
        String sql = "SELECT id_categoria, nombre, toneladas_maximas FROM categorias ORDER BY toneladas_maximas";

        // Consulta fija, sin parametros: alcanza con Statement
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                categorias.add(mapear(rs));
            }
        }
        return categorias;
    }

    @Override
    public Categoria buscarPorId(Connection con, int idCategoria) throws SQLException {
        String sql = "SELECT id_categoria, nombre, toneladas_maximas FROM categorias WHERE id_categoria = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idCategoria);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        }
        return null;
    }

    private Categoria mapear(ResultSet rs) throws SQLException {
        return new Categoria(
                rs.getInt("id_categoria"),
                rs.getString("nombre"),
                rs.getInt("toneladas_maximas"));
    }
}
