package negocio.modelo.dao.interfaces;

import java.sql.Connection;
import java.sql.SQLException;

public interface DistanciaDAO {

    // Devuelve los km entre dos destinos, o 0 si no hay dato cargado
    int obtenerKm(Connection con, int idOrigen, int idDestino) throws SQLException;
}
