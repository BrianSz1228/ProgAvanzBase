package negocio.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    private static ConexionBD instancia;

    private final String url;
    private final String usuario;
    private final String password;

    private ConexionBD() {
        //conexión con la bdd y datos en properties
        this.url = PropertiesUtil.get("db.url");
        this.usuario = PropertiesUtil.get("db.user");
        this.password = PropertiesUtil.get("db.password");

        try {
            Class.forName(PropertiesUtil.get("db.driver"));
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("No se encontro el driver JDBC", e);
        }
    }

    public static synchronized ConexionBD getInstance() {
        if (instancia == null) {
            instancia = new ConexionBD();
        }
        return instancia;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, usuario, password);
    }
}
