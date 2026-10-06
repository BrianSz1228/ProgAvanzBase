package negocio.modelo.dao.interfaces;

import negocio.modelo.dominio.Usuario;

import java.sql.Connection;
import java.sql.SQLException;

public interface UsuarioDAO {

    // Devuelve null si no existe
    Usuario buscarPorCredenciales(Connection con, String nombreUsuario, String password) throws SQLException;

    // Devuelve null si no existe
    Usuario buscarPorNombre(Connection con, String nombreUsuario) throws SQLException;

    // Devuelve el id generado
    int crear(Connection con, Usuario usuario) throws SQLException;

    void eliminar(Connection con, int idUsuario) throws SQLException;
}
