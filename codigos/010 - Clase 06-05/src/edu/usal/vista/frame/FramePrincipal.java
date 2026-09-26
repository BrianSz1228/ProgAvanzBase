package edu.usal.vista.frame;

import edu.usal.vista.panel.Formulario;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.Normalizer;

public class FramePrincipal extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JButton btnInterno;
    private JButton btnExterna;
    private JDesktopPane desktopPane;
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    FramePrincipal frame = new FramePrincipal();
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
    public FramePrincipal() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        JPanel panelSur = new JPanel();
        contentPane.add(panelSur, BorderLayout.SOUTH);

        btnInterno = new JButton("V.Interna");
        panelSur.add(btnInterno);
        btnInterno.addActionListener(this);

        btnExterna = new JButton("V. Externa");
        panelSur.add(btnExterna);
        btnExterna.addActionListener(this);

        JPanel panelCentral = new JPanel();
        contentPane.add(panelCentral, BorderLayout.CENTER);
        panelCentral.setLayout(new CardLayout(0, 0));

        desktopPane = new JDesktopPane();
        panelCentral.add(desktopPane, "name_3939219470536");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == this.btnInterno) {
            Formulario form = new Formulario();

            JInternalFrame internalFrame = new JInternalFrame("Venta Interna");
            internalFrame.setBounds(0, 0, 300, 450);
            internalFrame.setClosable(true);
            internalFrame.setMaximizable(true);
            internalFrame.setResizable(true);
            internalFrame.add(form);
            internalFrame.setVisible(true);

            this.desktopPane.add(internalFrame);
        }
        if(e.getSource() == this.btnExterna){
            Formulario form = new Formulario();
            JFrame ventaAux = new JFrame("Venta Externa");
            ventaAux.setDefaultCloseOperation(EXIT_ON_CLOSE);
            ventaAux.setBounds(0, 0, 350, 650);
            ventaAux.getContentPane().add(form);
            ventaAux.setVisible(true);
        }
    }
}
