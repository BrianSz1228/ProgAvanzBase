package edu.usal.vista.panel;

import javax.swing.*;
import java.awt.*;

public class Formulario extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTextField textField;
    private JTextField textField_1;

    /**
     * Create the panel.
     */
    public Formulario() {
        setLayout(new GridLayout(0, 2, 0, 0));

        JLabel lblNewLabel = new JLabel("New label");
        add(lblNewLabel);

        textField = new JTextField();
        add(textField);
        textField.setColumns(10);

        JLabel lblNewLabel_1 = new JLabel("New label");
        add(lblNewLabel_1);

        textField_1 = new JTextField();
        add(textField_1);
        textField_1.setColumns(10);

        JButton btnNewButton = new JButton("Enviar");
        add(btnNewButton);

    }

}
