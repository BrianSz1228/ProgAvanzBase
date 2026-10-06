package negocio.modelo.dao.factory;

import negocio.modelo.dao.implementaciones.*;
import negocio.modelo.dao.interfaces.*;
import negocio.util.PropertiesUtil;

/*
    Factory: devuelve el DAO segun el origen que dice el properties (dao.origen).
    Por ahora solo existe JDBC, para sumar otro origen (ej: MEM) se agrega
    aca la implementacion y no se toca el resto del codigo.
 */
public class DAOFactory {

    private static final String ORIGEN_JDBC = "JDBC";

    private DAOFactory() {
    }

    public static UsuarioDAO getUsuarioDAO() {
        verificarOrigen();
        return new UsuarioDAOImpl();
    }

    public static CategoriaDAO getCategoriaDAO() {
        verificarOrigen();
        return new CategoriaDAOImpl();
    }

    public static DestinoDAO getDestinoDAO() {
        verificarOrigen();
        return new DestinoDAOImpl();
    }

    public static DistanciaDAO getDistanciaDAO() {
        verificarOrigen();
        return new DistanciaDAOImpl();
    }

    public static ChoferDAO getChoferDAO() {
        verificarOrigen();
        return new ChoferDAOImpl();
    }

    public static CamionDAO getCamionDAO() {
        verificarOrigen();
        return new CamionDAOImpl();
    }

    public static ViajeDAO getViajeDAO() {
        verificarOrigen();
        return new ViajeDAOImpl();
    }

    private static void verificarOrigen() {
        String origen = PropertiesUtil.get("dao.origen");
        if (!ORIGEN_JDBC.equals(origen)) {
            throw new IllegalStateException("Implementacion inexistente para el origen: " + origen);
        }
    }
}
