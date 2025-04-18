package modelos;
import java.util.ArrayList;

public abstract class Algoritmo {
    private String nombre;
    private int quantum;

    public Algoritmo(String nombre) {
        this.nombre = nombre;
    }
    // constructor algoritmos con quantum
    public Algoritmo(String nombre, int quantum){
        this.nombre = nombre;
        this.quantum = quantum;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getQuantum(){
        return quantum;
    }

    public void setQuantum(int quantum){
        this.quantum = quantum;
    }

    //  ejecutar 
    public abstract ResultadoEjecucion ejecutar(ArrayList<BCP> procesos, int tiempoInicio);
    
}