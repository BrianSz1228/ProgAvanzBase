package edu.usal.hilos;

public class PrincipalTh {

    public static void main(String[] args) {
        HiloT h1 = new HiloT();
        HiloT h2 = new HiloT();
        HiloT h3 = new HiloT();

        h1.start();
        h2.start();
        h3.start();

    }
}
