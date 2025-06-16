package org.episs;

import javax.swing.*;
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

    public frmInsCli() {
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
                        limpiarCampos();
                    } else {
                        JOptionPane.showMessageDialog(jpClientes, "Error al insertar datos en la BD", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(jpClientes, "Error: datos incompletos", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        btnLimpiar.addActionListener(e -> limpiarCampos());
        btnCancelar.addActionListener(e -> ((JFrame) SwingUtilities.getWindowAncestor(jpClientes)).dispose());
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
