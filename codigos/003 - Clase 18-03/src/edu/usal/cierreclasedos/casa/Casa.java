package edu.usal.cierreclasedos.casa;

public class Casa {

    private Habitacion comedor;

    public Casa() {
        this.comedor = new Habitacion();
    }

    public Habitacion getComedor() {
        return comedor;
    }
}
