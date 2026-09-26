package edu.usal.excepciones;

public class PruebaExcepciones {

    public static void main(String[] args) {
        Cajero cajero = new Cajero();



        try {
            cajero.retirar(150000);
        } catch (SaldoInsuficienteException e) {
            e.printStackTrace();
            System.out.println("Excepcion : " + e.getMessage());
        } catch (LimiteDiarioException ex){
            System.out.println("WARNING : " + ex.getMessage());
        }

        System.out.println("FIN DE APP");
    }
}
