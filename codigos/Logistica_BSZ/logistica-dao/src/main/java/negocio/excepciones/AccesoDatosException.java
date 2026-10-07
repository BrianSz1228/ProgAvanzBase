package negocio.excepciones;

// Se usa cuando falla algo con la base de datos
public class AccesoDatosException extends Exception {

    public AccesoDatosException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
