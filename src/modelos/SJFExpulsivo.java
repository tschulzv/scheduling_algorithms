package modelos;
import java.util.*;

import utilidades.BCPUtils;

// Algoritmo de SJF Sin Desalojo
public class SJFExpulsivo extends Algoritmo {
    public SJFExpulsivo(String nombre){
        super(nombre);
    }

    @Override
    public ResultadoEjecucion ejecutar(ArrayList<BCP> procesos, int tiempoInicio){
        // crear una deep copy de los proceso pq este algoritmo hace 'remove' 
        // del arraylist y decrementa rafagas
        ArrayList<BCP> copia = BCPUtils.copiarLista(procesos);

        int reloj = tiempoInicio; // lleva la cuenta del tiempo actual
        int totalEjecucion = 0; 
        int totalEspera = 0;

        // crear objeto donde cargaremos los procesos con sus tiempos
        ResultadoEjecucion resultado = new ResultadoEjecucion("SJF Expulsivo");

        // crear una cola de prioridad con los BCP ordenados por cantidad de rafagas, y en caso de empate por llegada
        PriorityQueue<BCP> cola = new PriorityQueue<>(Comparator.comparingInt(BCP::getRafagas).thenComparingInt(BCP::getLlegada));
        // map donde guardaremos los tiempos 'trabajando' de cada proceso
        Map<String, List<Integer>> tiemposPorProceso = new HashMap<>(); 

        while (!copia.isEmpty() || !cola.isEmpty()){
            // en cada tiempo de reloj, vemos cuales procesos ya 'llegaron' y los ponemos en la cola
            Iterator<BCP> it = copia.iterator();
            while (it.hasNext()) {
                BCP p = it.next();
                if (p.getLlegada() <= reloj) {
                    cola.add(p);
                    it.remove();
                }
            }
     
            if (!cola.isEmpty()){
                // vemos los procesos en cola actualmente, sacar el de menor cant de rafagas 
                BCP actual = cola.poll(); //  guardamos el proceso actual
                // registrar este instante en la lista de tiempos
                tiemposPorProceso.putIfAbsent(actual.getNombre(), new ArrayList<>());
                tiemposPorProceso.get(actual.getNombre()).add(reloj);
                actual.setRafagas(actual.getRafagas() - 1); // decrementamos sus rafagas
               
                if (actual.getRafagas() == 0){ // si llega a 0 rafagas entonces sacamos de la cola
                    reloj++;
                    // tiempo de ejecucion es tiempo actual - tiempo llegada
                    totalEjecucion += (reloj - actual.getLlegada());
                    // su tiempo total de espera sería tiempo actual - rafagas - tiempo inicio
                    // hallamos el tiempo de rafagas del proceso original
                    BCP original = procesos.stream().filter(p -> p.getNombre().equals(actual.getNombre())).findFirst().orElse(null); 
                    if (original == null) {
                        System.out.println("ERROR Proceso inválido");
                        return null;
                    }
                    int totalRafagas = original.getRafagas();
                    totalEspera += (reloj - actual.getLlegada() - totalRafagas);
                    cola.remove(actual);
                } else { // volver a meter a la cola
                    reloj++;
                    cola.add(actual);
                }       
            } 
        }

        int cantProcesos = procesos.size();
        resultado.setFinTiempo(reloj - 1); // para saber cuantas columnas poner en la tabla
        resultado.setTiempoPromedioEjecucion(totalEjecucion / (float)cantProcesos);
        resultado.setTiempoPromedioEspera(totalEspera / (float)cantProcesos);
        resultado.setTiemposPorProceso(tiemposPorProceso); // pasarle el map de procesos y tiempos
        return resultado;
    }
    
}