package org.episs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class clsConexBD {
    private static final String url = "jdbc:mysql://localhost:3306/bdtienda?serverTimezone=UTC";
    private static final String usuario = "admin";
    private static final String contra = "admin";
    private static Connection con = null;

    public static Connection getConexBD(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, usuario, contra);
            System.out.println("Conexión exitosa a la BD");
        } catch(ClassNotFoundException e ) {
            System.out.println("No se encontró el driver JDBC");
        } catch(SQLException e){
            System.out.println("Error al conectar con la BD: " + e.getMessage());
        }
        return con;
    }

    public static void finConexBD(){
        try {
            if (con != null && !con.isClosed()) {
                con.close();
                System.out.println("Conexión cerrada");
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
}
