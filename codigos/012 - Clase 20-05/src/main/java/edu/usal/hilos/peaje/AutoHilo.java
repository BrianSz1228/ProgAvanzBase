package edu.usal.hilos.peaje;

import java.util.Random;

public class AutoHilo implements Runnable{
    private String nombre;
    private CabinaPeaje cabina;
    private Random random = new Random();

    public AutoHilo(String nombre, CabinaPeaje cabina) {
        this.nombre = nombre;
        this.cabina = cabina;
    }

    @Override
    public void run() {
        int tiempViaje = random.nextInt(3000);
        try {
            Thread.sleep(tiempViaje);
            cabina.pagar(this.nombre);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
