package edu.usal.clasedos;

public class ExportablePDF implements Exportable{
    @Override
    public void exportarAFile() {
        System.out.println("Exporto archivo a PDF");
    }
}
