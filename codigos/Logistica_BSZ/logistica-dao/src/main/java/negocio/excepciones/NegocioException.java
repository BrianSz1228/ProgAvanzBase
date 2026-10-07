package negocio.excepciones;

// Se usa cuando se rompe una regla de negocio (ej: chofer inexistente)
public class NegocioException extends Exception {

    public NegocioException(String mensaje) {
        super(mensaje);
    }
}
