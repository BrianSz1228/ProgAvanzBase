package edu.usal.clasedos;

public class PruebaInterface {

    public static void main(String[] args) {
        Exportable[] exp = {
          new ExportableCSV(),
          new ExportablePDF()
        };

        for(Exportable exportable : exp) {
            exportable.exportarAFile();
        }
    }
}
