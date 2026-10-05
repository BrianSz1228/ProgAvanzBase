package negocio.modelo.dao.implementaciones;

import negocio.modelo.dao.interfaces.UsuarioDAO;
import negocio.modelo.dominio.Perfil;
import negocio.modelo.dominio.Usuario;
import negocio.util.JdbcUtil;

import java.sql.*;

public class UsuarioDAOImpl implements UsuarioDAO {

    private static final String SELECT =
            "SELECT id_usuario, nombre_usuario, password, perfil FROM usuarios ";

    @Override
    public Usuario buscarPorCredenciales(Connection con, String nombreUsuario, String password) throws SQLException {
        String sql = SELECT + "WHERE nombre_usuario = ? AND password = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombreUsuario);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }

    @Override
    public Usuario buscarPorNombre(Connection con, String nombreUsuario) throws SQLException {
        String sql = SELECT + "WHERE nombre_usuario = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombreUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }

    @Override
    public int crear(Connection con, Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuarios (nombre_usuario, password, perfil) VALUES (?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, usuario.getNombreUsuario());
            ps.setString(2, usuario.getPassword());
            ps.setString(3, usuario.getPerfil().name());
            ps.executeUpdate();
            return JdbcUtil.obtenerIdGenerado(ps);
        }
    }

    @Override
    public void eliminar(Connection con, int idUsuario) throws SQLException {
        String sql = "DELETE FROM usuarios WHERE id_usuario = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ps.executeUpdate();
        }
    }

    private Usuario mapear(ResultSet rs) throws SQLException {
        return new Usuario(
                rs.getInt("id_usuario"),
                rs.getString("nombre_usuario"),
                rs.getString("password"),
                Perfil.valueOf(rs.getString("perfil")));
    }
}
