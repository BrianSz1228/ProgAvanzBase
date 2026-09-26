package edu.usal.basedatos;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlConector {

    /*
        Ciclo de vida de JDBC
        ----------------------------------------
        1 - Registrar Driver
        2 - Abrir conexion (String jdbc)
        3 - Crear Sentencia/s
        4 - Ejecutar Resultados
        5 - Cerrar recrusos
        6 - Manejo de excepciones
     */

    public static void main(String[] args) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        String urlJdbc = "jdbc:mysql://localhost:3306/adventureworks";
        String user = "root";
        String password = "123456";
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(urlJdbc,
                    user,
                    password);

            System.out.println("Conexion cerrada? " + connection.isClosed());

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            DatabaseMetaData metaData = connection.getMetaData();
            System.out.println("Nombre del producto: " + metaData.getDatabaseProductName());
            System.out.println("Version del producto: " + metaData.getDatabaseProductVersion());
            System.out.println("URL JDBC: " + metaData.getURL());

            connection.close();

            System.out.println("Conexion cerrada? " + connection.isClosed());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
