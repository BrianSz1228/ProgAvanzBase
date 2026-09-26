package edu.usal.hilos.peaje;

public class CabinaPeaje {

    public synchronized void pagar(String nombreAuto){
        System.out.println("[LLEGADA] --- " + nombreAuto + " ---- llegando a la cabina");
        try {
            System.out.println("[PAGANDO] --- " + nombreAuto +  " --- abonando peaje");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("[BARRERA] Pago con exito" + nombreAuto + " --- paso el peaje");
    }

}
