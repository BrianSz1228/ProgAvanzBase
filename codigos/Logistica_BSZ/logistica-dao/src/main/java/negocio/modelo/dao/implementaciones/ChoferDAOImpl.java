package negocio.modelo.dao.implementaciones;

import negocio.modelo.dao.interfaces.ChoferDAO;
import negocio.modelo.dominio.Categoria;
import negocio.modelo.dominio.Chofer;
import negocio.modelo.dominio.Perfil;
import negocio.modelo.dominio.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChoferDAOImpl implements ChoferDAO {

    // Trae el chofer junto con su usuario y su categoria
    private static final String SELECT =
            "SELECT c.id_chofer, c.nombre, c.apellido, c.dni, c.fecha_nacimiento, c.telefono_celular, " +
            "u.id_usuario, u.nombre_usuario, u.password, u.perfil, " +
            "cat.id_categoria, cat.nombre AS nombre_categoria, cat.toneladas_maximas " +
            "FROM choferes c " +
            "JOIN usuarios u ON u.id_usuario = c.id_usuario " +
            "JOIN categorias cat ON cat.id_categoria = c.id_categoria ";

    @Override
    public int crear(Connection con, Chofer chofer) throws SQLException {
        String sql = "INSERT INTO choferes (id_usuario, id_categoria, nombre, apellido, dni, " +
                "fecha_nacimiento, telefono_celular) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, chofer.getUsuario().getIdUsuario());
            ps.setInt(2, chofer.getCategoria().getIdCategoria());
            ps.setString(3, chofer.getNombre());
            ps.setString(4, chofer.getApellido());
            ps.setString(5, chofer.getDni());
            ps.setDate(6, Date.valueOf(chofer.getFechaNacimiento()));
            ps.setString(7, chofer.getTelefonoCelular());
            ps.executeUpdate();

            // Devuelvo el id que genero la bdd (AUTO_INCREMENT)
            try (ResultSet rs = ps.getGeneratedKeys()) {
                rs.next();
                return rs.getInt(1);
            }
        }
    }

    @Override
    public void actualizar(Connection con, Chofer chofer) throws SQLException {
        String sql = "UPDATE choferes SET id_categoria = ?, nombre = ?, apellido = ?, dni = ?, " +
                "fecha_nacimiento = ?, telefono_celular = ? WHERE id_chofer = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, chofer.getCategoria().getIdCategoria());
            ps.setString(2, chofer.getNombre());
            ps.setString(3, chofer.getApellido());
            ps.setString(4, chofer.getDni());
            ps.setDate(5, Date.valueOf(chofer.getFechaNacimiento()));
            ps.setString(6, chofer.getTelefonoCelular());
            ps.setInt(7, chofer.getIdChofer());
            ps.executeUpdate();
        }
    }

    @Override
    public void eliminar(Connection con, int idChofer) throws SQLException {
        String sql = "DELETE FROM choferes WHERE id_chofer = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idChofer);
            ps.executeUpdate();
        }
    }

    @Override
    public Chofer buscarPorId(Connection con, int idChofer) throws SQLException {
        String sql = SELECT + "WHERE c.id_chofer = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idChofer);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        }
        return null;
    }

    @Override
    public Chofer buscarPorDni(Connection con, String dni) throws SQLException {
        String sql = SELECT + "WHERE c.dni = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, dni);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        }
        return null;
    }

    // Se usa al iniciar sesion un chofer
    @Override
    public Chofer buscarPorUsuario(Connection con, int idUsuario) throws SQLException {
        String sql = SELECT + "WHERE c.id_usuario = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<Chofer> listarTodos(Connection con) throws SQLException {
        List<Chofer> choferes = new ArrayList<>();
        String sql = SELECT + "ORDER BY c.apellido, c.nombre";

        // Consulta fija, sin parametros: alcanza con Statement
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                choferes.add(mapear(rs));
            }
        }
        return choferes;
    }

    // Guarda en chofer_camion que el chofer puede manejar ese camion
    @Override
    public void autorizarCamion(Connection con, int idChofer, int idCamion) throws SQLException {
        String sql = "INSERT INTO chofer_camion (id_chofer, id_camion) VALUES (?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idChofer);
            ps.setInt(2, idCamion);
            ps.executeUpdate();
        }
    }

    @Override
    public void quitarAutorizaciones(Connection con, int idChofer) throws SQLException {
        String sql = "DELETE FROM chofer_camion WHERE id_chofer = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idChofer);
            ps.executeUpdate();
        }
    }

    // Paso la fila del ResultSet a un Chofer con su Usuario y su Categoria
    private Chofer mapear(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario(
                rs.getInt("id_usuario"),
                rs.getString("nombre_usuario"),
                rs.getString("password"),
                Perfil.valueOf(rs.getString("perfil")));

        Categoria categoria = new Categoria(
                rs.getInt("id_categoria"),
                rs.getString("nombre_categoria"),
                rs.getInt("toneladas_maximas"));

        return new Chofer(
                rs.getInt("id_chofer"),
                usuario,
                categoria,
                rs.getString("nombre"),
                rs.getString("apellido"),
                rs.getString("dni"),
                rs.getDate("fecha_nacimiento").toLocalDate(),
                rs.getString("telefono_celular"));
    }
}
