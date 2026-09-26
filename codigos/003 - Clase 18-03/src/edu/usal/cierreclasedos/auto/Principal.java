package edu.usal.cierreclasedos.auto;

import java.util.ArrayList;
import java.util.Arrays;

public class Principal {

    public static void main(String[] args) {
        Auto auto = new Auto(new Motor(),
                new ArrayList<>(
                        Arrays.asList(
                                new Neumatico(),
                                new Neumatico(),
                                new Neumatico(),
                                new Neumatico()
                        )
                ));

        auto.avanzar();
    }
}
