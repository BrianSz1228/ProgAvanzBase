package edu.usal.excepciones;

public class Cajero {

    private double saldo = 50000;
    private static final double LIMITE_DIARIO = 200000;



    public void retirar(double monto) throws SaldoInsuficienteException{

        if(monto > LIMITE_DIARIO) {
            throw new LimiteDiarioException("No podes extraer mas de limite diario " + LIMITE_DIARIO);
        }

        if(monto > saldo) {
            throw new SaldoInsuficienteException();
        }

        saldo = saldo - monto;

        System.out.println("Retiro exitoso. Nuevo saldo : " + saldo);
    }
}
