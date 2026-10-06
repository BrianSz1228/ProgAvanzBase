package negocio;

import negocio.util.ConexionBD;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// Clase temporal para probar la conexion, se puede borrar despues
public class PruebaConexion {

    public static void main(String[] args) {
        try (Connection con = ConexionBD.getInstance().getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT id_destino, nombre FROM destinos")) {

            System.out.println("Conectado a la base: " + con.getCatalog());
            while (rs.next()) {
                System.out.println(rs.getInt("id_destino") + " - " + rs.getString("nombre"));
            }
        } catch (SQLException e) {
            System.out.println("Error de conexion: " + e.getMessage());
        }
    }
}
