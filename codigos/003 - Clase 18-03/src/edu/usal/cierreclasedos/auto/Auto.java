package edu.usal.cierreclasedos.auto;

import java.util.List;

public class Auto implements Rodable{

    private String chasis;
    private Motor motor;
    private List<Neumatico> ruedas;

    public Auto(Motor motor, List<Neumatico> ruedas) {
        this.chasis = "CH-123";
        this.motor = motor;
        this.ruedas = ruedas;
    }

    @Override
    public void avanzar() {
        if(motor != null && ruedas.size() == 4){
            System.out.println("Avanza con chasis N° " + this.chasis);
        } else {
            System.out.println("Faltan componentes");
        }
    }
}
