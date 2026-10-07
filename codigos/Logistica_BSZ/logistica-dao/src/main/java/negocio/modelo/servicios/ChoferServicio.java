package negocio.modelo.servicios;

import negocio.excepciones.AccesoDatosException;
import negocio.excepciones.NegocioException;
import negocio.modelo.dao.factory.DAOFactory;
import negocio.modelo.dao.interfaces.CamionDAO;
import negocio.modelo.dao.interfaces.CategoriaDAO;
import negocio.modelo.dao.interfaces.ChoferDAO;
import negocio.modelo.dao.interfaces.UsuarioDAO;
import negocio.modelo.dominio.*;
import negocio.util.ConexionBD;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/*
    El alta, la modificacion y la baja de un chofer tocan varias tablas,
    por eso se hacen dentro de una transaccion (se guarda todo o nada)
 */
public class ChoferServicio {

    private ChoferDAO choferDAO;
    private UsuarioDAO usuarioDAO;
    private CamionDAO camionDAO;
    private CategoriaDAO categoriaDAO;

    public ChoferServicio() {
        this.choferDAO = DAOFactory.getChoferDAO();
        this.usuarioDAO = DAOFactory.getUsuarioDAO();
        this.camionDAO = DAOFactory.getCamionDAO();
        this.categoriaDAO = DAOFactory.getCategoriaDAO();
    }

    // crea el usuario, el chofer y sus camiones autorizados con commit y rollback por si surge algun error
    public void crear(Chofer chofer, List<Integer> idsCamiones) throws NegocioException, AccesoDatosException {
        // Primero valido los datos del chofer
        chofer.validar();

        try (Connection con = ConexionBD.getInstance().getConnection()) {
            try {
                // Inicio de la transaccion
                con.setAutoCommit(false);

                // 1er Paso - Traer la categoria completa
                cargarCategoria(con, chofer);

                // 2do Paso - Crear el usuario (el usuario de un chofer siempre tiene perfil CHOFER)
                Usuario usuario = chofer.getUsuario();
                usuario.setPerfil(Perfil.CHOFER);
                usuario.setIdUsuario(this.usuarioDAO.crear(con, usuario));

                // 3er Paso - Crear el chofer
                chofer.setIdChofer(this.choferDAO.crear(con, chofer));

                // 4to Paso - Autorizar los camiones elegidos
                autorizarCamiones(con, chofer, idsCamiones);

                con.commit();
            } catch (NegocioException | SQLException e) {
                // Si algo fallo se deshace todo lo anterior
                con.rollback();
                throw e;
            }
        } catch (SQLException e) {
            // 1062 error de mysql por un valor repetido (DNI o nombre de usuario)
            if (e.getErrorCode() == 1062) {
                throw new NegocioException("Ya existe un chofer con ese DNI o ese nombre de usuario");
            }
            throw new AccesoDatosException("No se pudo guardar el chofer", e);
        }
    }

    // actualiza los datos y vuelve a cargar los camiones autorizados
    public void actualizar(Chofer chofer, List<Integer> idsCamiones) throws NegocioException, AccesoDatosException {
        chofer.validar();

        try (Connection con = ConexionBD.getInstance().getConnection()) {
            try {
                con.setAutoCommit(false);

                // 1er Paso - Verificar que el chofer exista
                if (this.choferDAO.buscarPorId(con, chofer.getIdChofer()) == null) {
                    throw new NegocioException("El chofer no existe");
                }

                // 2do Paso - Actualizar los datos del chofer
                cargarCategoria(con, chofer);
                this.choferDAO.actualizar(con, chofer);

                // 3er Paso - Reemplazar los camiones autorizados
                this.choferDAO.quitarAutorizaciones(con, chofer.getIdChofer());
                autorizarCamiones(con, chofer, idsCamiones);

                con.commit();
            } catch (NegocioException | SQLException e) {
                // Si algo fallo se deshace todo lo anterior
                con.rollback();
                throw e;
            }
        } catch (SQLException e) {
            // 1062 error de mysql por un valor repetido (DNI)
            if (e.getErrorCode() == 1062) {
                throw new NegocioException("Ya existe un chofer con ese DNI");
            }
            throw new AccesoDatosException("No se pudo actualizar el chofer", e);
        }
    }

    // borra las autorizaciones, el chofer y su usuario
    public void eliminar(int idChofer) throws NegocioException, AccesoDatosException {
        try (Connection con = ConexionBD.getInstance().getConnection()) {
            try {
                con.setAutoCommit(false);

                Chofer chofer = this.choferDAO.buscarPorId(con, idChofer);
                if (chofer == null) {
                    throw new NegocioException("El chofer no existe");
                }

                this.choferDAO.quitarAutorizaciones(con, idChofer);
                this.choferDAO.eliminar(con, idChofer);
                this.usuarioDAO.eliminar(con, chofer.getUsuario().getIdUsuario());

                con.commit();
            } catch (NegocioException | SQLException e) {
                // Si algo fallo se deshace todo lo anterior
                con.rollback();
                throw e;
            }
        } catch (SQLException e) {
            // 1451 error de mysql que se está utilizando en otra tabla aun
            if (e.getErrorCode() == 1451) {
                throw new NegocioException("El chofer tiene viajes registrados y no se puede eliminar");
            }
            throw new AccesoDatosException("No se pudo eliminar el chofer", e);
        }
    }

    public Chofer buscarPorId(int idChofer) throws NegocioException, AccesoDatosException {
        try (Connection con = ConexionBD.getInstance().getConnection()) {
            Chofer chofer = this.choferDAO.buscarPorId(con, idChofer);
            if (chofer == null) {
                throw new NegocioException("El chofer no existe");
            }
            cargarCamiones(con, chofer);
            return chofer;
        } catch (SQLException e) {
            throw new AccesoDatosException("No se pudo buscar el chofer", e);
        }
    }

    public Chofer buscarPorDni(String dni) throws NegocioException, AccesoDatosException {
        try (Connection con = ConexionBD.getInstance().getConnection()) {
            Chofer chofer = this.choferDAO.buscarPorDni(con, dni);
            if (chofer == null) {
                throw new NegocioException("No existe un chofer con el DNI " + dni);
            }
            cargarCamiones(con, chofer);
            return chofer;
        } catch (SQLException e) {
            throw new AccesoDatosException("No se pudo buscar el chofer", e);
        }
    }

    // Se usa cuando entra un usuario con perfil CHOFER, para saber quien es
    public Chofer buscarPorUsuario(int idUsuario) throws NegocioException, AccesoDatosException {
        try (Connection con = ConexionBD.getInstance().getConnection()) {
            Chofer chofer = this.choferDAO.buscarPorUsuario(con, idUsuario);
            if (chofer == null) {
                throw new NegocioException("El usuario no tiene un chofer asociado");
            }
            cargarCamiones(con, chofer);
            return chofer;
        } catch (SQLException e) {
            throw new AccesoDatosException("No se pudo buscar el chofer", e);
        }
    }

    public List<Chofer> listar() throws AccesoDatosException {
        try (Connection con = ConexionBD.getInstance().getConnection()) {
            return this.choferDAO.listarTodos(con);
        } catch (SQLException e) {
            throw new AccesoDatosException("No se pudieron listar los choferes", e);
        }
    }

    // Para el combo de categorias del formulario
    public List<Categoria> listarCategorias() throws AccesoDatosException {
        try (Connection con = ConexionBD.getInstance().getConnection()) {
            return this.categoriaDAO.listarTodas(con);
        } catch (SQLException e) {
            throw new AccesoDatosException("No se pudieron cargar las categorías", e);
        }
    }

    // El formulario solo manda el id de la categoria, entonces busco su categoria
    private void cargarCategoria(Connection con, Chofer chofer) throws SQLException, NegocioException {
        Categoria categoria = this.categoriaDAO.buscarPorId(con, chofer.getCategoria().getIdCategoria());
        if (categoria == null) {
            throw new NegocioException("La categoría no existe");
        }
        chofer.setCategoria(categoria);
    }

    //  la categoria del chofer tiene que alcanzar para cada camion
    private void autorizarCamiones(Connection con, Chofer chofer, List<Integer> idsCamiones)
            throws SQLException, NegocioException {
        if (idsCamiones == null) {
            return;
        }
        for (int idCamion : idsCamiones) {
            Camion camion = this.camionDAO.buscarPorId(con, idCamion);
            if (camion == null) {
                throw new NegocioException("El camión seleccionado no existe");
            }
            if (!chofer.puedeManejar(camion)) {
                throw new NegocioException("La categoría " + chofer.getCategoria() + " no permite manejar el camión "
                        + camion + ", que lleva " + camion.getToneladasMaximas() + " toneladas");
            }
            this.choferDAO.autorizarCamion(con, chofer.getIdChofer(), idCamion);
        }
    }

    // La consulta del chofer no trae sus camiones, se buscan aparte
    private void cargarCamiones(Connection con, Chofer chofer) throws SQLException {
        chofer.setCamiones(this.camionDAO.listarAutorizadosPorChofer(con, chofer.getIdChofer()));
    }
}
