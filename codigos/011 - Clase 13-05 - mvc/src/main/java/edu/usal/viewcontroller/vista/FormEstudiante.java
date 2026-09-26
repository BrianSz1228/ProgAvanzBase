package edu.usal.viewcontroller.vista;

import javax.swing.*;
import java.awt.*;

public class FormEstudiante extends JPanel {

    private static final long serialVersionUID = 1L;
    public JTextField textDni;
    public JTextField txtNombre;
    public JButton btnAlta;

    /**
     * Create the panel.
     */
    public FormEstudiante() {
        setLayout(new GridLayout(3, 2, 0, 0));

        JLabel lblNewLabel = new JLabel("Nombre");
        add(lblNewLabel);

        txtNombre = new JTextField();
        add(txtNombre);
        txtNombre.setColumns(10);

        JLabel lblNewLabel_1 = new JLabel("DNI");
        add(lblNewLabel_1);

        textDni = new JTextField();
        add(textDni);
        textDni.setColumns(10);

        btnAlta = new JButton("Crear");
        add(btnAlta);

    }

}
