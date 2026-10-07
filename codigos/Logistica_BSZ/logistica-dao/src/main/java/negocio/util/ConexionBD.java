package negocio.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
    Singleton para la conexion a la bdd

    1 - Constructor privado
    2 - Atributo estatico con la unica instancia
    3 - Metodo getInstance() para acceder a ella
 */
public class ConexionBD {

    private static ConexionBD instancia;

    private String url;
    private String usuario;
    private String password;

    private ConexionBD() {
        //conexión con la bdd y datos en properties
        this.url = PropertiesUtil.getPropiedad("db.url");
        this.usuario = PropertiesUtil.getPropiedad("db.user");
        this.password = PropertiesUtil.getPropiedad("db.password");

        // Registro el driver
        try {
            Class.forName(PropertiesUtil.getPropiedad("db.driver"));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("No se encontro el driver JDBC", e);
        }
    }

    public static synchronized ConexionBD getInstance() {
        if (instancia == null) {
            instancia = new ConexionBD();
        }
        return instancia;
    }

    // Abre una conexion nueva, el que la pide es el que la cierra
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(this.url, this.usuario, this.password);
    }
}
