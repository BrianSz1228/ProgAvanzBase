package negocio.modelo.dao.interfaces;

import negocio.modelo.dominio.Categoria;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface CategoriaDAO {

    List<Categoria> listarTodas(Connection con) throws SQLException;

    Categoria buscarPorId(Connection con, int idCategoria) throws SQLException;
}
