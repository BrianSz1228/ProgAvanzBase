package edu.usal.clasetres;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ColeccionesApiStream {

    public static void main(String[] args) {
        //API STREAM

        /*Calculadora cal = new Calculadora() {
            @Override
            public double sumar(double a, double b) {
                return 0;
            }

            @Override
            public void mostrarValor(double valor) {

            }
        };*/

        Calculadora cal =
                (a, b) -> a + b;

        cal.sumar(5,5);





        List<String> invitados = new ArrayList<>(
                Arrays.asList(
                        "Lucas",
                        "Lucia",
                        "Roberto",
                        "Ana",
                        "Ariel",
                        "Mauro",
                        "alicia"
                )
        );

        //Programación imperativa

        List<String> comienzanConA = new ArrayList<>();

        for(String str : invitados) {
            if(str.toUpperCase().startsWith("A")){
                comienzanConA.add(str);
            }
        }

        System.out.println("Invitados con A: ");

        for(String str : comienzanConA){
            System.out.println(str);
        }

        //Programacion funcional
        List<String> streamConA = invitados.stream()
                .filter(invitado -> invitado.toUpperCase().startsWith("A"))

                .collect(Collectors.toList());

        streamConA.forEach(str -> System.out.println(str)); //Lambda convencional
        streamConA.forEach(System.out::println); //Lambda con referencia a metodo

        /*

        1 - Administrar una lista de vuelos. Algunos pueden estar duplicados por un error del
        sistema, y el aeropuerto necesita verlos ordenados por su codigo de vuelo de forma natural.
        Los vuelos tienen un codigo, destino y cantidad de asientos libres.
        Generar una Lista de 6 a 10 vuelos con codigo de vuelo duplicados y pasarlo a un Set con el fin de eliminar
        los duplicados.
        Filtrar los vuelos que tengan mas de 10 asientos libres, ordenarlos por codigo de vuelo y solamente mostrar
        por consola el destino.

        2 - Una portal de juegos necesita mostrar un ranking. El orden principal es por Puntaje, pero si hay dos
        con el mismo puntaje, el desempate es por Nombre alfabeticamente.
        Filtrar los jugadores con mas de 500 puntos, obtener solamente sus nickname en mayuscula.

         */
    }
}
