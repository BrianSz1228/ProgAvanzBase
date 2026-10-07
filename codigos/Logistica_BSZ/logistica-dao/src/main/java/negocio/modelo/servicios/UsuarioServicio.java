package negocio.modelo.servicios;

import negocio.excepciones.AccesoDatosException;
import negocio.excepciones.NegocioException;
import negocio.modelo.dao.factory.DAOFactory;
import negocio.modelo.dao.interfaces.UsuarioDAO;
import negocio.modelo.dominio.Usuario;
import negocio.util.ConexionBD;

import java.sql.Connection;
import java.sql.SQLException;

public class UsuarioServicio {

    private UsuarioDAO usuarioDAO;

    public UsuarioServicio() {
        this.usuarioDAO = DAOFactory.getUsuarioDAO();
    }

    // Si el usuario o la contraseña no coinciden se lanza NegocioException
    public Usuario login(String nombreUsuario, String password) throws NegocioException, AccesoDatosException {
        try (Connection con = ConexionBD.getInstance().getConnection()) {
            Usuario usuario = this.usuarioDAO.buscarPorCredenciales(con, nombreUsuario, password);
            if (usuario == null) {
                throw new NegocioException("Usuario o contraseña incorrectos");
            }
            return usuario;
        } catch (SQLException e) {
            throw new AccesoDatosException("No se pudo iniciar sesión", e);
        }
    }

    // Se usa con la cookie de "recordarme". Devuelve null si el usuario no existe
    public Usuario buscarPorNombre(String nombreUsuario) throws AccesoDatosException {
        try (Connection con = ConexionBD.getInstance().getConnection()) {
            return this.usuarioDAO.buscarPorNombre(con, nombreUsuario);
        } catch (SQLException e) {
            throw new AccesoDatosException("No se pudo buscar el usuario", e);
        }
    }
}
