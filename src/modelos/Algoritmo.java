import java.util.ArrayList;

public abstract class Algoritmo {

    private String nombre;

    public Algoritmo(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    //  ejecutar 
    public abstract ResultadoEjecucion ejecutar(ArrayList<BCP> procesos);
    // ejecutar con quantum
    public abstract ResultadoEjecucion ejecutar(ArrayList<BCP> procesos, int quantum);

}