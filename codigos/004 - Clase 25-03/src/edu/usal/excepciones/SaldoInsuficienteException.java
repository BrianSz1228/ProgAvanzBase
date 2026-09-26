package edu.usal.excepciones;

public class SaldoInsuficienteException extends Exception {

    public SaldoInsuficienteException() {
        super("Saldo insuficiente en la cuenta");
    }
}
