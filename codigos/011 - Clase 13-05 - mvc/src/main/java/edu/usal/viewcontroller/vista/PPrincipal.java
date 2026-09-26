package edu.usal.viewcontroller.vista;

import edu.usal.modelo.dao.factory.EstudianteFactory;
import edu.usal.modelo.dao.interfaces.EstudianteDAO;
import edu.usal.viewcontroller.controlador.EstudianteControlador;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PPrincipal extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JMenuBar barra;
    private JMenu mEstudiante;
    private JMenuItem miEstudiante;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    PPrincipal frame = new PPrincipal();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public PPrincipal() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);

        this.barra = new JMenuBar();
        this.miEstudiante = new JMenuItem("Alta estudiante");
        this.miEstudiante.addActionListener(this);
        this.mEstudiante = new JMenu("Estudiantes");

        this.mEstudiante.add(this.miEstudiante);

        this.barra.add(mEstudiante);

        setJMenuBar(this.barra);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == this.miEstudiante) {
            JFrame frame = new JFrame("Alta de estudiante");

            //Instancio el modelo
            EstudianteDAO modelo = EstudianteFactory.getEstudianteFactory("MEM");

            //Instancio la vista
            FormEstudiante formEstudiante = new FormEstudiante();

            //Instancio controlador y evento
            EstudianteControlador controlador = new EstudianteControlador(modelo, formEstudiante);
            FormEstudianteEvento evento = new FormEstudianteEvento(formEstudiante, controlador);


            frame.getContentPane().add(formEstudiante);
            frame.setBounds(0, 0, 850, 450);
            frame.setVisible(true);
        }
    }
}
