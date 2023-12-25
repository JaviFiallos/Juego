package modelo;

import clases.Combinacion;
import clases.Conexion;
import clases.Configuracion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ConfiguracionDAO extends Conexion {

    private Connection con = getConexion();
    private PreparedStatement ps;
    private ResultSet rs;

    public boolean guardarConfiguracion(Configuracion c) {

        String sql = "UPDATE configuracion SET TIEMPO_SEG=?, PUNTOxTIEMPO=?, PUNTOxACIERTO=? WHERE ID=?";
        try {

            ps = con.prepareStatement(sql);

            ps.setInt(1, c.getTiempo());
            ps.setInt(2, c.getPunto_Tiempo());
            ps.setInt(3, c.getPunto_Acierto());
            ps.setInt(4, c.getId());
            if (ps.executeUpdate() > 0) {
                System.out.println("si");
                return true;
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public boolean agregarCombinacion(Combinacion c) {

        String sql = "INSERT INTO combinacion (COMBINACION, PLAYER, ID_DIFICULTAD) VALUES (?,?,?)";
        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, c.getCombinacion());
            ps.setInt(2, c.getPlayer());
            ps.setInt(3, c.getDificultad());

            if (ps.executeUpdate() > 0) {
                return true;
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public boolean eliminarCombinacion(int id) {

        String sql = "DELETE FROM combinacion WHERE ID =?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            if (ps.executeUpdate() > 0) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public List listarCombinaciones(int dificultad) {

        String sql = "SELECT ID, COMBINACION, PLAYER FROM combinacion WHERE ID_DIFICULTAD= " + dificultad + " ORDER BY PLAYER ASC";
        List<Combinacion> lista = new ArrayList<>();

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Combinacion pro = new Combinacion();
                pro.setId(rs.getInt(1));
                pro.setCombinacion(rs.getString(2));
                pro.setPlayer(rs.getInt(3));
                lista.add(pro);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return lista;
    }

    public Configuracion listarConfiguraciones(int dificultad) {

        String sql = "SELECT ID, TIEMPO_SEG, PUNTOxTIEMPO, PUNTOxACIERTO FROM configuracion WHERE ID= " + dificultad;
        Configuracion pro=null;
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                pro = new Configuracion();
                pro.setId(rs.getInt(1));
                pro.setTiempo(rs.getInt(2));
                pro.setPunto_Tiempo(rs.getInt(3));
                pro.setPunto_Acierto(rs.getInt(4));
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return pro;
    }

}
