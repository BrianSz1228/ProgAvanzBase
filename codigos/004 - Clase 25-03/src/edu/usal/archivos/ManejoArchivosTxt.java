package edu.usal.archivos;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ManejoArchivosTxt {

    /*
        File = Solamente representa el archivo o directorio
        FileWriter / FileReader = Abrir el canal para escribir o leer el archivo
        BufferedWrtiter / BufferedReader = Escribir o leer sobre archivo fisico
     */

    public static void main(String[] args) throws IOException {
        String nombreArchivo = "datos.txt";

        File file = new File(nombreArchivo);
        if(file.exists()) {
            System.out.println("Archivo no existes");
        } else {
            file.createNewFile();
        }

        FileWriter fWriter = new FileWriter(file);
        BufferedWriter bWriter = new BufferedWriter(fWriter);

        bWriter.write("Primer oracion de mi archivo txt");
        bWriter.newLine();

        bWriter.close();
        fWriter.close();

    }
}
