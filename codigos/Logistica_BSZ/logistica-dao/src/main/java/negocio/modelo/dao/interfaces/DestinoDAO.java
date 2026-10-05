package negocio.modelo.dao.interfaces;

import negocio.modelo.dominio.Destino;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface DestinoDAO {

    List<Destino> listarTodos(Connection con) throws SQLException;

    Destino buscarPorId(Connection con, int idDestino) throws SQLException;
}
