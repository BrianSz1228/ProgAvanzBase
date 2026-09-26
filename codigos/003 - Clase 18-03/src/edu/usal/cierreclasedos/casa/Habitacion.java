package edu.usal.cierreclasedos.casa;


import java.util.ArrayList;
import java.util.List;

public class Habitacion {
    private List<Dispositivo> dispositivos = new ArrayList<>();

    public void agregarDispositivo(Dispositivo dispositivo){
        this.dispositivos.add(dispositivo);
    }

    public void modoNoche(){
        this.dispositivos.forEach(Dispositivo::encender);
    }

}
