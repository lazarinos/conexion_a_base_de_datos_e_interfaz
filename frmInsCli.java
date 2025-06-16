package org.episs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class frmInsCli {
    public JPanel jpClientes;
    private JTextField jtxtNombre;
    private JTextField jtxtEmail;
    private JButton btnLimpiar;
    private JButton btnConf;
    private JButton btnCancelar;

    private intCliInsertadoCallback escuCallBack;

    public frmInsCli(intCliInsertadoCallback objEscu) {
        escuCallBack = objEscu;
        agregarEventos();
    }

    private void agregarEventos() {
        btnConf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = jtxtNombre.getText().trim();
                String email = jtxtEmail.getText().trim();

                if (!nombre.isEmpty() && !email.isEmpty()) {
                    if (insCliBD(nombre, email)) {
                        JOptionPane.showMessageDialog(jpClientes, "Cliente insertado con éxito", "MENSAJE", JOptionPane.INFORMATION_MESSAGE);

                        // Notificar al callback que un cliente fue insertado
                        if (escuCallBack != null) {
                            escuCallBack.cliInsertado();
                        }

                        // Cerrar solo esta ventana
                        Window ven = SwingUtilities.getWindowAncestor(jpClientes);
                        if (ven != null) ven.dispose();

                    } else {
                        JOptionPane.showMessageDialog(jpClientes, "Error al insertar datos en la BD", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(jpClientes, "Error al insertar datos, revisar", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        btnLimpiar.addActionListener(e -> limpiarCampos());

        btnCancelar.addActionListener(e -> {
            Window ven = SwingUtilities.getWindowAncestor(jpClientes);
            if (ven != null) ven.dispose();
        });
    }

    private boolean insCliBD(String nom, String email) {
        String sql = "INSERT INTO Clientes(nombre, email) VALUES (?, ?)";

        try {
            Connection con = clsConexBD.getConexBD();
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, nom);
            pstmt.setString(2, email);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar cliente: " + e.getMessage());
            return false;
        } finally {
            clsConexBD.finConexBD();
        }
    }

    private void limpiarCampos() {
        jtxtNombre.setText("");
        jtxtEmail.setText("");
    }
}
