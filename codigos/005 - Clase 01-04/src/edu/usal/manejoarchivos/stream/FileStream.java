package edu.usal.manejoarchivos.stream;

import edu.usal.manejoarchivos.string.Persona;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class FileStream {

    public static void main(String[] args) {
        Properties properties = new Properties();
        try {
            properties.load(new FileReader("application.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String fileName = String.valueOf(properties.get("file-persona"));

        /*Persona persona = new Persona("Lucia", "Lopez", 12345L);
        escribir(persona);*/

        List<Persona> personas = leerPersona(fileName);
        personas.forEach(System.out::println);
    }

    public static void escribir(Persona persona){
        File file = new File("personas.dat");
        FileOutputStream fOutputStream = null;
        ObjectOutputStream oOutputStream = null;

        try {
            fOutputStream = new FileOutputStream(file);
            oOutputStream = new ObjectOutputStream(fOutputStream);
            oOutputStream.writeObject(persona);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Persona> leerPersona(String fileName){
        File file = new File(fileName);
        FileInputStream fInputStream = null;
        ObjectInputStream oInputStream = null;
        List<Persona> personas = new ArrayList<>();

        try {
            fInputStream = new FileInputStream(file);
            oInputStream = new ObjectInputStream(fInputStream);
            Persona persona = (Persona) oInputStream.readObject();
            personas.add(persona);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return personas;
    }
}
