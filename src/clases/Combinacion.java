
package clases;

public class Combinacion {
    
    private int id;
    private String combinacion;
    private int player;
    private int dificultad;

    public Combinacion(String combinacion, int player, int dificultad) {
        this.combinacion = combinacion;
        this.player = player;
        this.dificultad = dificultad;
    }

    public Combinacion() {
    }

    public Combinacion(int id, String combinacion, int player, int dificultad) {
        this.id = id;
        this.combinacion = combinacion;
        this.player = player;
        this.dificultad = dificultad;
    }

    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    

    public String getCombinacion() {
        return combinacion;
    }

    public void setCombinacion(String combinacion) {
        this.combinacion = combinacion;
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public int getDificultad() {
        return dificultad;
    }

    public void setDificultad(int dificultad) {
        this.dificultad = dificultad;
    }
    
    
}
