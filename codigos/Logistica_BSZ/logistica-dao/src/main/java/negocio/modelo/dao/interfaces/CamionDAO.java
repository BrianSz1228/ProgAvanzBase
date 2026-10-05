package negocio.modelo.dao.interfaces;

import negocio.modelo.dominio.Camion;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface CamionDAO {

    // Devuelve el id generado
    int crear(Connection con, Camion camion) throws SQLException;

    void actualizar(Connection con, Camion camion) throws SQLException;

    void eliminar(Connection con, int idCamion) throws SQLException;

    // Devuelve null si no existe
    Camion buscarPorId(Connection con, int idCamion) throws SQLException;

    List<Camion> listarTodos(Connection con) throws SQLException;

    // Camiones que el chofer puede manejar
    List<Camion> listarAutorizadosPorChofer(Connection con, int idChofer) throws SQLException;

    // Los autorizados que ademas no tienen un viaje sin finalizar
    List<Camion> listarDisponiblesParaChofer(Connection con, int idChofer) throws SQLException;
}
