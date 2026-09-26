package edu.usal.clasedos;

public class ExportableCSV implements Exportable{
    @Override
    public void exportarAFile() {
        System.out.println("Exporto archvio a CSV");
    }
}
