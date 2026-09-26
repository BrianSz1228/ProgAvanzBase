package edu.usal.cierreclasedos.casa;

public class Principal {

    public static void main(String[] args) {
        Casa casa = new Casa();
        casa.getComedor().agregarDispositivo(new Lampara());
        casa.getComedor().modoNoche();
    }
}
