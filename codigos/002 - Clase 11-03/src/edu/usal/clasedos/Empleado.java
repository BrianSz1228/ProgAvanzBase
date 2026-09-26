package edu.usal.clasedos;

public abstract class Empleado {

    protected String nombre;
    protected Double sueldoBase;

    public Empleado(String nombre, Double sueldoBase) {
        this.nombre = nombre;
        this.sueldoBase = sueldoBase;
    }

    public void mostrarDatos(){
        System.out.println("Nombre empleado : " + this.nombre);
    }

    public abstract double calcularSueldo(); //Obligar a sobre-escribir el metodo en la clase hija
}
