package edu.usal.manejoarchivos.string;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileString {




    public static void main(String[] args) {
        /*escribir();
        List<String> lineas = leer();
        lineas.forEach(System.out::println);*/


        /*Persona persona = new Persona("Nombre2", "Apellido2", 222222L);
        escribirPersona(persona);*/


        List<Persona> personas = leerPersonas();
        personas.forEach(System.out::println);
    }


    public static void escribir(){
        File file = new File("clase05.txt");
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            fileWriter = new FileWriter(file, true);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("linea de texto para el archivo \n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                bufferedWriter.close();
                fileWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static List<String> leer(){
        File file = new File("clase05.txt");
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        List<String> lineas = new ArrayList<>();
        try {
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
            String linea;
            while((linea = bufferedReader.readLine()) != null){
                lineas.add(linea);
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                fileReader.close();
                bufferedReader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return lineas;
    }

    public static void escribirPersona(Persona persona){
        File file = new File("personas.txt");
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            fileWriter = new FileWriter(file, true);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(personaToString(persona));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                bufferedWriter.close();
                fileWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public static List<Persona> leerPersonas(){
        File file = new File("personas.txt");
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        List<Persona> personas = new ArrayList<>();
        try {
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
            String linea;
            while((linea = bufferedReader.readLine()) != null){
                personas.add(stringToPersona(linea));
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                fileReader.close();
                bufferedReader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return personas;
    }

    //-SERIALIZACION
    private static String personaToString(Persona persona) {
        return persona.getNombre() + ";" +
                persona.getApellido() + ";" +
                persona.getDni() + "\n";
    }

    //DESERIALIZACION
    private static Persona stringToPersona(String linea){
        String[] split = linea.split(";");
        return new Persona(split[0],
                split[1],
                Long.valueOf(split[2]));
    }

}
