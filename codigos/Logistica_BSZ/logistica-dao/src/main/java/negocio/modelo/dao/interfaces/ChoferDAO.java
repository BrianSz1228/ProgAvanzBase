package negocio.modelo.dao.interfaces;

import negocio.modelo.dominio.Chofer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface ChoferDAO {

    // El chofer ya tiene que traer su usuario con id. Devuelve el id generado
    int crear(Connection con, Chofer chofer) throws SQLException;

    void actualizar(Connection con, Chofer chofer) throws SQLException;

    void eliminar(Connection con, int idChofer) throws SQLException;

    // Los tres buscar devuelven null si no existe
    Chofer buscarPorId(Connection con, int idChofer) throws SQLException;

    Chofer buscarPorDni(Connection con, String dni) throws SQLException;

    Chofer buscarPorUsuario(Connection con, int idUsuario) throws SQLException;

    List<Chofer> listarTodos(Connection con) throws SQLException;

    // Camiones que el chofer puede manejar (tabla chofer_camion)
    void autorizarCamion(Connection con, int idChofer, int idCamion) throws SQLException;

    void quitarAutorizaciones(Connection con, int idChofer) throws SQLException;
}
