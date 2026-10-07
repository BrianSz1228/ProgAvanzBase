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

    private static final String ORIGEN = PropertiesUtil.getPropiedad("dao.origen");

    public static UsuarioDAO getUsuarioDAO() {
        if (ORIGEN.equals("JDBC")) {
            return new UsuarioDAOImpl();
        }
        throw new RuntimeException("Implementación inexistente");
    }

    public static CategoriaDAO getCategoriaDAO() {
        if (ORIGEN.equals("JDBC")) {
            return new CategoriaDAOImpl();
        }
        throw new RuntimeException("Implementación inexistente");
    }

    public static DestinoDAO getDestinoDAO() {
        if (ORIGEN.equals("JDBC")) {
            return new DestinoDAOImpl();
        }
        throw new RuntimeException("Implementación inexistente");
    }

    public static DistanciaDAO getDistanciaDAO() {
        if (ORIGEN.equals("JDBC")) {
            return new DistanciaDAOImpl();
        }
        throw new RuntimeException("Implementación inexistente");
    }

    public static ChoferDAO getChoferDAO() {
        if (ORIGEN.equals("JDBC")) {
            return new ChoferDAOImpl();
        }
        throw new RuntimeException("Implementación inexistente");
    }

    public static CamionDAO getCamionDAO() {
        if (ORIGEN.equals("JDBC")) {
            return new CamionDAOImpl();
        }
        throw new RuntimeException("Implementación inexistente");
    }

    public static ViajeDAO getViajeDAO() {
        if (ORIGEN.equals("JDBC")) {
            return new ViajeDAOImpl();
        }
        throw new RuntimeException("Implementación inexistente");
    }
}
