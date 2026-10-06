package negocio.modelo.servicios;

import negocio.excepciones.AccesoDatosException;
import negocio.excepciones.NegocioException;
import negocio.modelo.dao.factory.DAOFactory;
import negocio.modelo.dao.interfaces.CamionDAO;
import negocio.modelo.dominio.Camion;
import negocio.util.ConexionBD;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CamionServicio {

    private final CamionDAO camionDAO = DAOFactory.getCamionDAO();

    public void crear(Camion camion) throws NegocioException, AccesoDatosException {
        try (Connection con = ConexionBD.getInstance().getConnection()) {
            camion.setIdCamion(camionDAO.crear(con, camion));
        } catch (SQLException e) {
            // 1062 error de mysql que ya existe un id
            if (e.getErrorCode() == 1062) {
                throw new NegocioException("Ya existe un camión con ese dominio");
            }
            throw new AccesoDatosException("No se pudo guardar el camión", e);
        }
    }

    public void actualizar(Camion camion) throws NegocioException, AccesoDatosException {
        try (Connection con = ConexionBD.getInstance().getConnection()) {
            if (camionDAO.buscarPorId(con, camion.getIdCamion()) == null) {
                throw new NegocioException("El camión no existe");
            }
            camionDAO.actualizar(con, camion);
        } catch (SQLException e) {
            // 1062 error de mysql que ya existe un id
            if (e.getErrorCode() == 1062) {
                throw new NegocioException("Ya existe un camión con ese dominio");
            }
            throw new AccesoDatosException("No se pudo actualizar el camión", e);
        }
    }

    public void eliminar(int idCamion) throws NegocioException, AccesoDatosException {
        try (Connection con = ConexionBD.getInstance().getConnection()) {
            if (camionDAO.buscarPorId(con, idCamion) == null) {
                throw new NegocioException("El camión no existe");
            }
            camionDAO.eliminar(con, idCamion);
        } catch (SQLException e) {
            // 1451 error de myslq que se está utilizando en otra tabla
            if (e.getErrorCode() == 1451) {
                throw new NegocioException("El camión tiene viajes registrados y no se puede eliminar");
            }
            throw new AccesoDatosException("No se pudo eliminar el camión", e);
        }
    }

    public Camion buscarPorId(int idCamion) throws NegocioException, AccesoDatosException {
        try (Connection con = ConexionBD.getInstance().getConnection()) {
            Camion camion = camionDAO.buscarPorId(con, idCamion);
            if (camion == null) {
                throw new NegocioException("El camión no existe");
            }
            return camion;
        } catch (SQLException e) {
            throw new AccesoDatosException("No se pudo buscar el camión", e);
        }
    }

    public List<Camion> listar() throws AccesoDatosException {
        try (Connection con = ConexionBD.getInstance().getConnection()) {
            return camionDAO.listarTodos(con);
        } catch (SQLException e) {
            throw new AccesoDatosException("No se pudieron listar los camiones", e);
        }
    }

    // Camiones que el chofer puede manejar y que no estan en un viaje
    public List<Camion> listarDisponiblesParaChofer(int idChofer) throws AccesoDatosException {
        try (Connection con = ConexionBD.getInstance().getConnection()) {
            return camionDAO.listarDisponiblesParaChofer(con, idChofer);
        } catch (SQLException e) {
            throw new AccesoDatosException("No se pudieron listar los camiones disponibles", e);
        }
    }
}
