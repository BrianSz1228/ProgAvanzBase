package edu.usal.hilos.peaje;

public class SimulacionPeaje {

    public static void main(String[] args) {
        System.out.println(" ABRIENDO AUTOPISTA Y PEAJES ");

        CabinaPeaje cabinaPeaje = new CabinaPeaje();

        Thread auto1 = new Thread(new AutoHilo("Hilux", cabinaPeaje));
        Thread auto2 = new Thread(new AutoHilo("Focus", cabinaPeaje));
        Thread auto3 = new Thread(new AutoHilo("Cronos", cabinaPeaje));
        Thread auto4 = new Thread(new AutoHilo("Gol", cabinaPeaje));
        Thread auto5 = new Thread(new AutoHilo("Fun", cabinaPeaje));

        auto1.start();
        auto2.start();
        auto3.start();
        auto4.start();
        auto5.start();


    }
}
