package negocio.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

// Lee los datos del archivo config.properties (origen del DAO y conexion a la bdd)
public class PropertiesUtil {

    private static final String ARCHIVO = "config.properties";

    public static String getPropiedad(String clave) {
        Properties propiedades = new Properties();

        try (InputStream entrada = PropertiesUtil.class.getClassLoader().getResourceAsStream(ARCHIVO)) {
            if (entrada == null) {
                throw new RuntimeException("No se encontro el archivo " + ARCHIVO);
            }
            propiedades.load(entrada);
        } catch (IOException e) {
            throw new RuntimeException("No se pudo leer el archivo " + ARCHIVO, e);
        }

        return propiedades.getProperty(clave);
    }
}
