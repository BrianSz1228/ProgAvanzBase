package negocio.modelo.dao.interfaces;

import negocio.modelo.dominio.EstadoViaje;
import negocio.modelo.dominio.Viaje;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface ViajeDAO {

    // Devuelve el id generado
    int crear(Connection con, Viaje viaje) throws SQLException;

    // Devuelve null si no existe
    Viaje buscarPorId(Connection con, int idViaje) throws SQLException;

    List<Viaje> listarTodos(Connection con) throws SQLException;

    List<Viaje> listarPorChofer(Connection con, int idChofer) throws SQLException;

    // Cambia el estado y guarda la fecha de inicio o de fin segun corresponda
    void actualizarEstado(Connection con, int idViaje, EstadoViaje estado) throws SQLException;

    // Llama al stored procedure. Devuelve un texto que empieza con OK: o ERROR:
    String validarAsignacion(Connection con, int idChofer, int idCamion) throws SQLException;
}
