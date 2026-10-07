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

    private ViajeDAO viajeDAO;
    private ChoferDAO choferDAO;
    private CamionDAO camionDAO;
    private DestinoDAO destinoDAO;
    private DistanciaDAO distanciaDAO;

    public ViajeServicio() {
        this.viajeDAO = DAOFactory.getViajeDAO();
        this.choferDAO = DAOFactory.getChoferDAO();
        this.camionDAO = DAOFactory.getCamionDAO();
        this.destinoDAO = DAOFactory.getDestinoDAO();
        this.distanciaDAO = DAOFactory.getDistanciaDAO();
    }

    // El admin le asigna un viaje a un chofer con cálculo de km, dias y tanques
    public Viaje crearViaje(int idChofer, int idCamion, int idOrigen, int idDestino)
            throws NegocioException, AccesoDatosException {
        // El origen y el destino tienen que ser distintos
        if (idOrigen == idDestino) {
            throw new NegocioException("El origen y el destino no pueden ser iguales");
        }

        try (Connection con = ConexionBD.getInstance().getConnection()) {
            try {
                // Inicio de la transaccion
                con.setAutoCommit(false);

                // 1er Paso - Buscar el chofer y el camion
                Chofer chofer = this.choferDAO.buscarPorId(con, idChofer);
                if (chofer == null) {
                    throw new NegocioException("El chofer no existe");
                }
                Camion camion = this.camionDAO.buscarPorId(con, idCamion);
                if (camion == null) {
                    throw new NegocioException("El camión no existe");
                }

                // 2do Paso - La categoria del chofer tiene que alcanzar para el camion
                if (!chofer.puedeManejar(camion)) {
                    throw new NegocioException("La categoría " + chofer.getCategoria() + " no permite manejar el camión "
                            + camion + ", que lleva " + camion.getToneladasMaximas() + " toneladas");
                }

                // 3er Paso - El SP revisa que este autorizado y que el camion no tenga otro viaje
                String resultado = this.viajeDAO.validarAsignacion(con, idChofer, idCamion);
                if (!resultado.startsWith("OK")) {
                    // El mensaje viene como "ERROR: xx", se saca el ERROR: para que quede limpio
                    throw new NegocioException(resultado.replace("ERROR:", "").trim());
                }

                // 4to Paso - Buscar los km en la tabla de distancias
                int km = this.distanciaDAO.obtenerKm(con, idOrigen, idDestino);
                if (km <= 0) {
                    throw new NegocioException("No hay una distancia cargada entre el origen y el destino elegidos");
                }

                // 5to Paso - Armar el viaje, calcular dias y tanques y guardarlo
                Destino origen = this.destinoDAO.buscarPorId(con, idOrigen);
                Destino destino = this.destinoDAO.buscarPorId(con, idDestino);

                Viaje viaje = new Viaje(chofer, camion, origen, destino);
                viaje.calcularRecorrido(km);
                viaje.setIdViaje(this.viajeDAO.crear(con, viaje));

                con.commit();
                return viaje;
            } catch (NegocioException | SQLException e) {
                // Si algo fallo se deshace todo lo anterior
                con.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new AccesoDatosException("No se pudo guardar el viaje", e);
        }
    }

    // El chofer inicia un viaje que tenia asignado
    public void iniciarViaje(int idViaje, int idChofer) throws NegocioException, AccesoDatosException {
        try (Connection con = ConexionBD.getInstance().getConnection()) {
            Viaje viaje = this.viajeDAO.buscarPorId(con, idViaje);
            if (viaje == null) {
                throw new NegocioException("El viaje no existe");
            }
            if (viaje.getChofer().getIdChofer() != idChofer) {
                throw new NegocioException("El viaje no pertenece a este chofer");
            }
            if (viaje.getEstado() != EstadoViaje.ASIGNADO) {
                throw new NegocioException("Solo se puede iniciar un viaje que está asignado");
            }
            this.viajeDAO.actualizarEstado(con, idViaje, EstadoViaje.EN_CURSO);
        } catch (SQLException e) {
            throw new AccesoDatosException("No se pudo iniciar el viaje", e);
        }
    }

    // El chofer finaliza un viaje que tenia en curso
    public void finalizarViaje(int idViaje, int idChofer) throws NegocioException, AccesoDatosException {
        try (Connection con = ConexionBD.getInstance().getConnection()) {
            Viaje viaje = this.viajeDAO.buscarPorId(con, idViaje);
            if (viaje == null) {
                throw new NegocioException("El viaje no existe");
            }
            if (viaje.getChofer().getIdChofer() != idChofer) {
                throw new NegocioException("El viaje no pertenece a este chofer");
            }
            if (viaje.getEstado() != EstadoViaje.EN_CURSO) {
                throw new NegocioException("Solo se puede finalizar un viaje que está en curso");
            }
            this.viajeDAO.actualizarEstado(con, idViaje, EstadoViaje.FINALIZADO);
        } catch (SQLException e) {
            throw new AccesoDatosException("No se pudo finalizar el viaje", e);
        }
    }

    // Para los combos de origen y destino del formulario
    public List<Destino> listarDestinos() throws AccesoDatosException {
        try (Connection con = ConexionBD.getInstance().getConnection()) {
            return this.destinoDAO.listarTodos(con);
        } catch (SQLException e) {
            throw new AccesoDatosException("No se pudieron cargar los destinos", e);
        }
    }

    public List<Viaje> listarTodos() throws AccesoDatosException {
        try (Connection con = ConexionBD.getInstance().getConnection()) {
            return this.viajeDAO.listarTodos(con);
        } catch (SQLException e) {
            throw new AccesoDatosException("No se pudieron listar los viajes", e);
        }
    }

    public List<Viaje> listarPorChofer(int idChofer) throws AccesoDatosException {
        try (Connection con = ConexionBD.getInstance().getConnection()) {
            return this.viajeDAO.listarPorChofer(con, idChofer);
        } catch (SQLException e) {
            throw new AccesoDatosException("No se pudieron listar los viajes del chofer", e);
        }
    }
}
