package edu.usal.hilos;

public class HiloR implements Runnable{

    private String nombreHilo;

    public HiloR(String nombreHilo) {
        this.nombreHilo = nombreHilo;
    }

    @Override
    public void run() {
        for(int i = 0; i < 15; i++){
            System.out.println("Valor " + i + "\tTH.RUN - " + nombreHilo);
        }
    }

}
