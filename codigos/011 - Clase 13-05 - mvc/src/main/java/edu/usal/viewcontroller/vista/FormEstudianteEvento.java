package edu.usal.viewcontroller.vista;

import edu.usal.viewcontroller.controlador.EstudianteControlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormEstudianteEvento implements ActionListener {

    private FormEstudiante vista;
    private EstudianteControlador controlador;

    public FormEstudianteEvento(FormEstudiante vista, EstudianteControlador controlador) {
        this.vista = vista;
        this.controlador = controlador;

        this.vista.btnAlta.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == this.vista.btnAlta){
            this.controlador.registroEstudiante();
        }
    }
}
