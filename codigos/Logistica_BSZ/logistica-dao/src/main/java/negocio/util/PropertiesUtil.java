package negocio.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {

    private static final String ARCHIVO = "config.properties";
    private static final Properties propiedades = new Properties();

    // Se lee el archivo una sola vez, al cargar la clase
    static {
        try (InputStream in = PropertiesUtil.class.getClassLoader().getResourceAsStream(ARCHIVO)) {
            if (in == null) {
                throw new IllegalStateException("No se encontro el archivo de propiedadess " + ARCHIVO);
            }
            propiedades.load(in);
        } catch (IOException e) {
            throw new IllegalStateException("No se pudo leer el archivo " + ARCHIVO, e);
        }
    }

    private PropertiesUtil() {
    }

    public static String get(String clave) {
        return propiedades.getProperty(clave);
    }
}
