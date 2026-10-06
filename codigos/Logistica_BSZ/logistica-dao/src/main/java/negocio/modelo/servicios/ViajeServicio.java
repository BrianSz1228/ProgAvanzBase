package negocio.modelo.servicios;

import negocio.excepciones.AccesoDatosException;
import negocio.excepciones.NegocioException;
import negocio.modelo.dao.factory.DAOFactory;
import negocio.modelo.dao.interfaces.*;
import negocio.modelo.dominio.*;
import negocio.util.ConexionBD;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ViajeServicio {

    private final ViajeDAO viajeDAO = DAOFactory.getViajeDAO();
    private final ChoferDAO choferDAO = DAOFactory.getChoferDAO();
    private final CamionDAO camionDAO = DAOFactory.getCamionDAO();
    private final DestinoDAO destinoDAO = DAOFactory.getDestinoDAO();
    private final DistanciaDAO distanciaDAO = DAOFactory.getDistanciaDAO();

    // El admin le asigna un viaje a un chofer con calulo de km, dias y tanques
    public Viaje crearViaje(int idChofer, int idCamion, int idOrigen, int idDestino)
            throws NegocioException, AccesoDatosException {
        try (Connection con = ConexionBD.getInstance().getConnection()) {
            try {
                con.setAutoCommit(false);

                Chofer chofer = choferDAO.buscarPorId(con, idChofer);
                if (chofer == null) {
                    throw new NegocioException("El chofer no existe");
                }
                Camion camion = camionDAO.buscarPorId(con, idCamion);
                if (camion == null) {
                    throw new NegocioException("El camión no existe");
                }

                //  la categoria del chofer tiene que alcanzar para el camion
                if (!chofer.puedeManejar(camion)) {
                    throw new NegocioException("La categoría " + chofer.getCategoria() + " no permite manejar el camión "
                            + camion + ", que lleva " + camion.getToneladasMaximas() + " toneladas");
                }

                // la funcion revisa que este autorizado y que el camion no tenga otro viaje
                String resultado = viajeDAO.validarAsignacion(con, idChofer, idCamion);
                if (!resultado.startsWith("OK")) {
                    // El mensaje viene como "ERROR: xx", se saca el ERROR: para que quede limpio
                    throw new NegocioException(resultado.replace("ERROR:", "").trim());
                }

                // Si origen y destino son iguales tampoco hay distancia cargada
                int km = distanciaDAO.obtenerKm(con, idOrigen, idDestino);
                if (km <= 0) {
                    throw new NegocioException("No hay una distancia cargada entre el origen y el destino elegidos");
                }

                Destino origen = destinoDAO.buscarPorId(con, idOrigen);
                Destino destino = destinoDAO.buscarPorId(con, idDestino);

                Viaje viaje = new Viaje(chofer, camion, origen, destino);
                viaje.calcularRecorrido(km);
                viaje.setIdViaje(viajeDAO.crear(con, viaje));

                con.commit();
                return viaje;
            } catch (NegocioException | SQLException e) {
                con.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new AccesoDatosException("No se pudo guardar el viaje", e);
        }
    }

    public void iniciarViaje(int idViaje, int idChofer) throws NegocioException, AccesoDatosException {
        cambiarEstado(idViaje, idChofer, EstadoViaje.ASIGNADO, EstadoViaje.EN_CURSO,
                "Solo se puede iniciar un viaje que está asignado");
    }

    public void finalizarViaje(int idViaje, int idChofer) throws NegocioException, AccesoDatosException {
        cambiarEstado(idViaje, idChofer, EstadoViaje.EN_CURSO, EstadoViaje.FINALIZADO,
                "Solo se puede finalizar un viaje que está en curso");
    }

    // Para los combos de origen y destino del formulario
    public List<Destino> listarDestinos() throws AccesoDatosException {
        try (Connection con = ConexionBD.getInstance().getConnection()) {
            return destinoDAO.listarTodos(con);
        } catch (SQLException e) {
            throw new AccesoDatosException("No se pudieron cargar los destinos", e);
        }
    }

    public List<Viaje> listarTodos() throws AccesoDatosException {
        try (Connection con = ConexionBD.getInstance().getConnection()) {
            return viajeDAO.listarTodos(con);
        } catch (SQLException e) {
            throw new AccesoDatosException("No se pudieron listar los viajes", e);
        }
    }

    public List<Viaje> listarPorChofer(int idChofer) throws AccesoDatosException {
        try (Connection con = ConexionBD.getInstance().getConnection()) {
            return viajeDAO.listarPorChofer(con, idChofer);
        } catch (SQLException e) {
            throw new AccesoDatosException("No se pudieron listar los viajes del chofer", e);
        }
    }

    // El viaje tiene que ser del chofer y estar en el estado que corresponde
    private void cambiarEstado(int idViaje, int idChofer, EstadoViaje estado, EstadoViaje nuevo,
                               String mensajeEstado) throws NegocioException, AccesoDatosException {
        try (Connection con = ConexionBD.getInstance().getConnection()) {
            Viaje viaje = viajeDAO.buscarPorId(con, idViaje);
            if (viaje == null) {
                throw new NegocioException("El viaje no existe");
            }
            if (viaje.getChofer().getIdChofer() != idChofer) {
                throw new NegocioException("El viaje no pertenece a este chofer");
            }
            if (viaje.getEstado() != estado) {
                throw new NegocioException(mensajeEstado);
            }
            viajeDAO.actualizarEstado(con, idViaje, nuevo);
        } catch (SQLException e) {
            throw new AccesoDatosException("No se pudo cambiar el estado del viaje", e);
        }
    }
}
