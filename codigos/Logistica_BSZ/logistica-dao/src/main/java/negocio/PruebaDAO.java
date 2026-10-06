package negocio;

import negocio.modelo.dao.factory.DAOFactory;
import negocio.modelo.dao.interfaces.*;
import negocio.modelo.dominio.Chofer;
import negocio.util.ConexionBD;

import java.sql.Connection;
import java.sql.SQLException;

// Clase temporal para probar los DAO, se puede borrar despues
public class PruebaDAO {

    public static void main(String[] args) {
        UsuarioDAO usuarioDAO = DAOFactory.getUsuarioDAO();
        ChoferDAO choferDAO = DAOFactory.getChoferDAO();
        CamionDAO camionDAO = DAOFactory.getCamionDAO();
        DistanciaDAO distanciaDAO = DAOFactory.getDistanciaDAO();
        ViajeDAO viajeDAO = DAOFactory.getViajeDAO();

        try (Connection con = ConexionBD.getInstance().getConnection()) {
            System.out.println("Login correcto: " + usuarioDAO.buscarPorCredenciales(con, "admin", "admin123"));
            System.out.println("Login incorrecto: " + usuarioDAO.buscarPorCredenciales(con, "admin", "xxx"));

            Chofer chofer = choferDAO.buscarPorDni(con, "32333444");
            System.out.println("Chofer: " + chofer + " - categoria " + chofer.getCategoria());
            System.out.println("Disponibles: " + camionDAO.listarDisponiblesParaChofer(con, chofer.getIdChofer()));

            System.out.println("Km CABA - Cordoba: " + distanciaDAO.obtenerKm(con, 1, 2));
            System.out.println("SP camion 2: " + viajeDAO.validarAsignacion(con, chofer.getIdChofer(), 2));
            System.out.println("SP camion 4: " + viajeDAO.validarAsignacion(con, chofer.getIdChofer(), 4));
            System.out.println("Viajes cargados: " + viajeDAO.listarTodos(con).size());
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
