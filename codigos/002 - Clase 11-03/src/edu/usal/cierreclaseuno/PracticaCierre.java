package edu.usal.cierreclaseuno;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class PracticaCierre {

    public static void main(String[] args) {
        /*
            Extraer usuario de un email y validar dominio ".edu.ar".
         */

        String email = "alumno.string@usal.edu.ar";
        String[] split = email.split("@");
        System.out.println("Usuario : " + split[0]);
        System.out.println("Es dominio .edu.ar : " + split[1].contains(".edu.ar"));


        String dominio = email.substring(email.indexOf("@")+1);
        String usuario = email.substring(0, email.indexOf("@"));

        System.out.println("usuario : " + usuario + "Dominio : " + dominio);
        /*
            Unir nombres de productos válidos.
         */



        String[] productos = {"Laptop", "", null, "Mouse", "Teclado"};
        StringBuilder unirProductos = new StringBuilder();
        for(String str : productos){
            if(str != null && !str.isEmpty()){
                unirProductos.append(str).append("\t");
            }
        }

        System.out.println("Products validos : " + unirProductos.toString());
        /*
            Calcular los dias vividos en 10 años
         */

        LocalDate hoy = LocalDate.now();
        LocalDate enDiezAnios = hoy.plusYears(10);
        long dias = ChronoUnit.DAYS.between(hoy, enDiezAnios);
        System.out.println("Dias en 10 años : " + dias);

    }
}
