package negocio;

import negocio.excepciones.AccesoDatosException;
import negocio.excepciones.NegocioException;
import negocio.modelo.dominio.*;
import negocio.modelo.servicios.ChoferServicio;
import negocio.modelo.servicios.UsuarioServicio;
import negocio.modelo.servicios.ViajeServicio;

import java.time.LocalDate;
import java.util.Arrays;

// Clase temporal para probar los servicios, se puede borrar despues
public class PruebaServicios {

    public static void main(String[] args) {
        UsuarioServicio usuarioServicio = new UsuarioServicio();
        ChoferServicio choferService = new ChoferServicio();
        ViajeServicio viajeService = new ViajeServicio();

        try {
            // 1 - Login
            Usuario maria = usuarioServicio.login("mgomez", "1234");
            Chofer chofer = choferService.buscarPorUsuario(maria.getIdUsuario());
            System.out.println("1 - Entro: " + chofer + " - camiones: " + chofer.getCamiones());

            // 2 - Viaje valido: CABA (1) a Cordoba (2) con el camion 2
            Viaje viaje = viajeService.crearViaje(chofer.getIdChofer(), 2, 1, 2);
            System.out.println("2 - Viaje " + viaje.getIdViaje() + ": " + viaje.getKm() + " km, "
                    + viaje.getDias() + " dias, " + viaje.getTanques() + " tanque/s");

            // 3 - El mismo camion otra vez: tiene que fallar
            intentar("3 - Camion ocupado", () -> viajeService.crearViaje(chofer.getIdChofer(), 2, 1, 3));

            // 4 - Camion que la categoria C2 no alcanza
            intentar("4 - Categoria", () -> viajeService.crearViaje(chofer.getIdChofer(), 3, 1, 3));

            // 5 - Iniciar y finalizar
            viajeService.iniciarViaje(viaje.getIdViaje(), chofer.getIdChofer());
            viajeService.finalizarViaje(viaje.getIdViaje(), chofer.getIdChofer());
            System.out.println("5 - Viajes del chofer: " + viajeService.listarPorChofer(chofer.getIdChofer()));

            // 6 - Alta que falla a mitad: el usuario no tiene que quedar guardado (rollback)
            Categoria c1 = new Categoria();
            c1.setIdCategoria(1);
            Usuario nuevo = new Usuario(0, "prueba_rollback", "1234", Perfil.CHOFER);
            Chofer prueba = new Chofer(0, nuevo, c1, "Prueba", "Rollback", "99999999",
                    LocalDate.of(1990, 1, 1), "1100000000");
            intentar("6 - Alta con camion de mas peso", () -> choferService.crear(prueba, Arrays.asList(4)));
            System.out.println("6 - Usuario guardado? " + (usuarioServicio.buscarPorNombre("prueba_rollback") != null));

        } catch (NegocioException e) {
            System.out.println("Regla de negocio: " + e.getMessage());
        } catch (AccesoDatosException e) {
            System.out.println("Error de datos: " + e.getMessage() + " / " + e.getCause());
        }
    }

    private interface Accion {
        void ejecutar() throws NegocioException, AccesoDatosException;
    }

    private static void intentar(String titulo, Accion accion) {
        try {
            accion.ejecutar();
            System.out.println(titulo + ": no fallo (revisar)");
        } catch (NegocioException e) {
            System.out.println(titulo + " -> " + e.getMessage());
        } catch (AccesoDatosException e) {
            System.out.println(titulo + " -> error de datos: " + e.getMessage());
        }
    }
}
