package modelos;

public class BCP {
    private String nombre; // nombre
    private int llegada; // tiempo t de llegada
    private int rafagas; // rafagas a ejecutar
    private int prioridad; // prioridad
    

    public BCP(String nombre, int llegada, int rafagas, int prioridad) {
        this.nombre = nombre;
        this.llegada = llegada;
        this.rafagas = rafagas;
        this.prioridad = prioridad;
    }

    // getters y setters
    public String getNombre() {
            return nombre;
    }

    public int getLlegada() {
        return llegada;
    }

    public int getRafagas() {
        return rafagas;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setLlegada(int llegada) {
        this.llegada = llegada;
    }

    public void setRafagas(int rafagas) {
        this.rafagas = rafagas;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

}