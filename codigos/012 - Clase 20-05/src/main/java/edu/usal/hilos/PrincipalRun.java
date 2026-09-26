package edu.usal.hilos;

public class PrincipalRun {

    public static void main(String[] args) {
        HiloR run1 = new HiloR("R1");
        HiloR run2 = new HiloR("R2");
        HiloR run3 = new HiloR("R3");

        Thread t1 = new Thread(run1);
        Thread t2 = new Thread(run2);
        Thread t3 = new Thread(run3);

        t1.start();
        t2.start();
        t3.start();


    }
}
