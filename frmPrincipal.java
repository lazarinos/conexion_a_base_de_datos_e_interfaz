package org.episs;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class frmPrincipal {
    public JPanel jpPrincipal;
    private JTable jtClientes;
    private JLabel lblClientes;
    private JTable jtPedidos;
    private JButton btnIns;
    private DefaultTableModel modelo;
    private DefaultTableModel modPedidos;

    public frmPrincipal() {
        lblClientes.setText("Listado de Clientes");

        modelo = new DefaultTableModel();
        modelo.addColumn("ID Clientes");
        modelo.addColumn("Nombre");
        modelo.addColumn("Correo elect.");
        jtClientes.setModel(modelo);

        modPedidos = new DefaultTableModel();
        modPedidos.addColumn("ID Pedido");
        modPedidos.addColumn("ID Clientes");
        modPedidos.addColumn("Producto");
        modPedidos.addColumn("Cantidad");
        jtPedidos.setModel(modPedidos);

        agregarEventos();
        cargarDatos();
    }

    private void cargarDatos() {
        modelo.setRowCount(0);
        String sql = "SELECT * FROM Clientes";

        try (Connection con = clsConexBD.getConexBD();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                modelo.addRow(new Object[]{
                        rs.getInt("id_cliente"),
                        rs.getString("nombre"),
                        rs.getString("email")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar datos: " + e.getMessage());
        } finally {
            clsConexBD.finConexBD();
        }
    }

    private void agregarEventos() {
        jtClientes.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && jtClientes.getSelectedRow() != -1) {
                int fila = jtClientes.getSelectedRow();
                int idCliente = (int) jtClientes.getValueAt(fila, 0);
                cargarPedidos(idCliente);
            }
        });

        btnIns.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame objFC = new JFrame("Gestión de Clientes");
                frmInsCli objFic = new frmInsCli(() -> cargarDatos());
                objFC.setContentPane(objFic.jpClientes);
                objFC.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                objFC.pack();
                objFC.setLocationRelativeTo(null);
                objFC.setVisible(true);
            }
        });
    }

    private void cargarPedidos(int idCliente) {
        modPedidos.setRowCount(0);
        String sql = "SELECT * FROM pedidos WHERE id_cliente = ?";

        try (Connection con = clsConexBD.getConexBD();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, idCliente);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                modPedidos.addRow(new Object[]{
                        rs.getInt("id_pedido"),
                        rs.getInt("id_cliente"),
                        rs.getString("producto"),
                        rs.getInt("cantidad")
                });
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar pedidos: " + e.getMessage());
        } finally {
            clsConexBD.finConexBD();
        }
    }
}
