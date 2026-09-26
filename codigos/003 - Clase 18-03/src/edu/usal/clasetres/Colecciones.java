package edu.usal.clasetres;

import java.lang.reflect.Array;
import java.util.*;

public class Colecciones {

    public static void main(String[] args) {
        String[] nombres = new String[6];

        List<String> apellidos = new ArrayList<>();

        Set<String> dni = new HashSet<>();

        Map<String, String> diccionario = new HashMap<>();

        apellidos.add("Lopez");
        apellidos.add("Gomez");
        apellidos.add("Arbia");
        apellidos.add("Leon");

        for(String str : apellidos){
            System.out.println(str);
        }

        apellidos.add("Leon");


        /*for(String str : apellidos){
            System.out.println(str);
        }*/

        for(String str : apellidos) {
            if(str.equals("Leon")){
                apellidos.remove(str);
                break;
            }
        }

        System.out.println("***********************");

        for(String str : apellidos){
            System.out.println(str);
        }

        Iterator<String> iterator = apellidos.iterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }

        dni.add("Lopez");
        dni.add("Gomez");
        dni.add("Leon");
        dni.add("Leon");

        for(String str : dni){
            System.out.println(str);
        }

        dni.add("Arbia");

        System.out.println("*****************");

        for(String str : dni){
            System.out.println(str);
        }
    }
}
