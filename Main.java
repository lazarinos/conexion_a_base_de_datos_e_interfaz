package org.episs;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame objFP = new JFrame("Ventana Principal");
        objFP.setContentPane(new frmPrincipal().jpPrincipal);
        objFP.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        objFP.pack();
        objFP.setLocationRelativeTo(null);
        objFP.setVisible(true);
    }
}
