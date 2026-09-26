package edu.usal.cierreclaseuno;

public class RendimientoString {
    public static void main(String[] args) {
        int iteraciones = 10000;

        System.out.println("Inicio de performance con String");

        long inicioStr = System.currentTimeMillis();
        String str = "";
        for(int i = 0; i < iteraciones; i++){
            str += i;
        }
        long finStr = System.currentTimeMillis();

        System.out.println("Tiempo consumido con String :" + (finStr - inicioStr) + " ms");

        System.out.println("Inicio de performance con StringBuilder");

        long incioStrBuilder = System.currentTimeMillis();
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < iteraciones; i++) {
            stringBuilder.append(i);
        }
        long finStrBuilder = System.currentTimeMillis();

        System.out.println("Tiempo consumido con StringBuilder : " + (finStrBuilder - incioStrBuilder) + " ms");
    }
}
