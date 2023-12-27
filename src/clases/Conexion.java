package clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Conexion {

    private final String BASE = "pm";
    private final String USER = "root";
    private final String PASSWORD = "081005";
    private final String URL = "jdbc:mysql://127.0.0.1/" + BASE;
    private Connection con = null;

    public Connection getConexion() throws SQLException {
    try {
        con = DriverManager.getConnection(this.URL, this.USER, this.PASSWORD);
        if (con != null) {
            System.out.println("Se conectó");
        }
    } catch (SQLException ex) {
        // Loguea el error o lanza una excepción personalizada
        throw new SQLException("Error al establecer la conexión: " + ex.getMessage());
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
                System.out.println("Se cerró");
            }
        }
    }

    public Combinacion obtenerCombinacionAleatoria(int player, int dificultad) {
    String query = "SELECT * FROM combinaciones WHERE player = ? AND dificultad = ? ORDER BY RAND() LIMIT 1";

    try (PreparedStatement pst = con.prepareStatement(query)) {
        pst.setInt(1, player);
        pst.setInt(2, dificultad);
        try (ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {
                int id = rs.getInt("id");
                String combinacion = rs.getString("combinacion");
                return new Combinacion(id, combinacion, player, dificultad);
            }
        }
    } catch (SQLException ex) {
        // Loguea el error o lanza una excepción personalizada
        System.out.println(ex);
    }

    return null;
    }
}
