
package clases;


public class Configuracion {
    
    private int id;
    private int tiempo;
    private int punto_Tiempo;
    private int punto_Acierto;

    public Configuracion() {
    }

    public Configuracion(int id, int tiempo, int punto_Tiempo, int punto_Acierto) {
        this.id = id;
        this.tiempo = tiempo;
        this.punto_Tiempo = punto_Tiempo;
        this.punto_Acierto = punto_Acierto;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    public int getPunto_Tiempo() {
        return punto_Tiempo;
    }

    public void setPunto_Tiempo(int punto_Tiempo) {
        this.punto_Tiempo = punto_Tiempo;
    }

    public int getPunto_Acierto() {
        return punto_Acierto;
    }

    public void setPunto_Acierto(int punto_Acierto) {
        this.punto_Acierto = punto_Acierto;
    }
    
    
}
