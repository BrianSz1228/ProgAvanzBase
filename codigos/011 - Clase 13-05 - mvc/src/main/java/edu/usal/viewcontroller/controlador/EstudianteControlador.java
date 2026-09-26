package edu.usal.viewcontroller.controlador;

import edu.usal.modelo.dao.interfaces.EstudianteDAO;
import edu.usal.modelo.dominio.Estudiante;
import edu.usal.viewcontroller.vista.FormEstudiante;

import javax.swing.*;

public class EstudianteControlador {

    private EstudianteDAO modelo;
    private FormEstudiante vista;

    public EstudianteControlador(EstudianteDAO modelo, FormEstudiante vista) {
        this.modelo = modelo;
        this.vista = vista;
    }

    public void registroEstudiante(){
        String nombre = this.vista.txtNombre.getText();
        String dni = this.vista.textDni.getText();
        Estudiante estudiante = new Estudiante(null, nombre, dni);
        boolean resultado = this.modelo.crearEstudiante(estudiante);
        if(resultado){
            JOptionPane.showMessageDialog(null, "Estudiante creado correctamente");
            this.vista.textDni.setText("");
            this.vista.txtNombre.setText("");
        }
    }

}
