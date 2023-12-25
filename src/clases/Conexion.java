package clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    
    private final String BASE = "mecanografia";
    private final String USER = "root";
    private final String PASSWORD = "1805162433";
    private final String URL = "jdbc:mysql://127.0.0.1/" + BASE;
    private Connection con = null;

    public Connection getConexion() {   

        try {
            con = DriverManager.getConnection(this.URL, this.USER, this.PASSWORD);
            if (con != null) {
            System.out.println("Se conecto");
        }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        return con;
    }

    public boolean testConnection() {
        try {
            con = (Connection) DriverManager.getConnection(this.URL, this.USER, this.PASSWORD);
            con.close();
            return true; // La conexión se estableció y se cerró correctamente
        } catch (SQLException e) {
            System.err.println(e);
            return false; // Ocurrió un error al establecer la conexión
        }
    }

    public void cerrarConexion() throws SQLException {

        if (con != null) {
            if (!con.isClosed()) {
                con.close();
                System.out.println("Se cerro");
            }

        }
    }
}
