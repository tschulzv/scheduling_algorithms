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
    private Map<String, List<Integer>> tiemposPorProceso = new LinkedHashMap<>();
    private float tiempoPromedioEjecucion;
    private float tiempoPromedioEspera;

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
}
