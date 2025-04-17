package modelos;
import java.util.*;

// clase auxiliar para compartir información entre el algoritmo y el diagrama
/* ATRIBUTO tiemposPorProceso: 
    - String es el nombre del proceso
    - List<Integer> es la lista de los tiempos en los que el proceso se ejecutó
    por ejemplo:
      "procesoA" -> [0, 1, 2, 3, 8, 9, 10, 11]
      "procesoB" -> [4, 5, 6, 7, 12, 13]
*/
public class ResultadoEjecucion {
    private String nombreAlgoritmo;
    private Map<String, List<Integer>> tiemposPorProceso = new LinkedHashMap<>();
    private float tiempoPromedioEjecucion;
    private float tiempoPromedioEspera;
    // usamos este campo para saber cuantas columnas agregar a la tabla de resultado
    private int finTiempo; 

    public ResultadoEjecucion(String nombreAlgoritmo){
        this.nombreAlgoritmo = nombreAlgoritmo;
    }

    public ResultadoEjecucion(String nombreAlgoritmo, Map<String, List<Integer>> tiemposPorProceso, float tiempoPromedioEjecucion, float tiempoPromedioEspera){
        this.nombreAlgoritmo = nombreAlgoritmo;
        this.tiemposPorProceso = tiemposPorProceso;
        this.tiempoPromedioEjecucion = tiempoPromedioEjecucion;
        this.tiempoPromedioEspera = tiempoPromedioEspera;
    }

    public String getNombreAlgoritmo(){
        return nombreAlgoritmo;
    }

    public void setNombreAlgoritmo(String nombre){
        this.nombreAlgoritmo = nombre;
    }

    public Map<String, List<Integer>> getTiemposPorProceso() {
        return tiemposPorProceso;
    }

    public void setTiemposPorProceso(Map<String, List<Integer>> tiemposPorProceso) {
        this.tiemposPorProceso = tiemposPorProceso;
    }

    public void addTiempos(String nombreProceso, List<Integer> tiempos) {
        this.tiemposPorProceso.put(nombreProceso, tiempos);
    }

    public float getTiempoPromedioEjecucion() {
        return tiempoPromedioEjecucion;
    }

    public void setTiempoPromedioEjecucion(float tiempoPromedioEjecucion) {
        this.tiempoPromedioEjecucion = tiempoPromedioEjecucion;
    }

    public float getTiempoPromedioEspera() {
        return tiempoPromedioEspera;
    }

    public void setTiempoPromedioEspera(float tiempoPromedioEspera) {
        this.tiempoPromedioEspera = tiempoPromedioEspera;
    }

    public int getFinTiempo(){
        return finTiempo;
    }

    public void setFinTiempo(int finTiempo){
        this.finTiempo = finTiempo;
    }

    // funcion para crear los encabezados para JTable
    public String[] getEncabezados(){

        // creamos finTiempo + 2 columnas
        // por ej, si finTiempo = 3 -> nombreProceso, 0, 1, 2, 3 
        String [] columnas = new String[finTiempo + 2];
        columnas[0] = "Proceso";

        // poner los numeros de los tiempos
        for (int i = 1; i < columnas.length; i++){
            columnas[i] = String.valueOf(i - 1);
        }
        
        return columnas;
    }

    // funcion para convertir resultados a un formato valido para JTable
    public Object[][] getMatriz(){
        Object[][] matriz = new Object[tiemposPorProceso.size()][finTiempo + 2];

        int fila = 0; // iniciar valor para recorrer filas
        // recorremos el map de procesos y tiempos, cargando en cada fila
        for (Map.Entry<String, List<Integer>> proc : tiemposPorProceso.entrySet()) {
            String nombre = proc.getKey(); // obtener el nombre del proceso
            List<Integer> tiempos = proc.getValue(); // lista de tiempos en los que tuvo rafagas

            matriz[fila][0] = nombre; // la primera celda tiene nombre del proceso
            for (int i = 1; i <= finTiempo + 1; i++){
                matriz[fila][i] = ""; // inicializar cada celda con espacio vacio
            }

            // luego, ponemos una X en los lugares donde el proceso estuvo haciendo sus rafagas
            for (int tiempo : tiempos) {
                matriz[fila][tiempo + 1] = "X"; // +1 porque columna 0 es el nombre
            }
            fila++; // avanzar a la siguiente fila de la matriz
        }
        
        return matriz;
    }
    // Combina varios resultados de ejecución - para Cola Multinivel
    // recibe un vargargs de varios resultados
    public static ResultadoEjecucion mergeResultados(ResultadoEjecucion... resultados) {
        Map<String, List<Integer>> tiemposTotales = new LinkedHashMap<>();
        float totalEspera = 0;
        float totalEjecucion = 0;
        int cantidadProcesos = 0;
    
        for (ResultadoEjecucion r : resultados) {
            tiemposTotales.putAll(r.getTiemposPorProceso());
            totalEspera += r.getTiempoPromedioEspera() * r.getTiemposPorProceso().size();
            totalEjecucion += r.getTiempoPromedioEjecucion() * r.getTiemposPorProceso().size();
            cantidadProcesos += r.getTiemposPorProceso().size();
        }
    
        float promedioEspera = totalEspera / cantidadProcesos;
        float promedioEjecucion = totalEjecucion / cantidadProcesos;
    
        return new ResultadoEjecucion("Multinivel", tiemposTotales, promedioEjecucion, promedioEspera);
    }
    

    
}
