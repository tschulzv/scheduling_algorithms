package modelos;
import java.util.*;

// Algoritmo de Round Robin
public class RR extends Algoritmo {
    private int quantum; //tiempo asignado a cada proceso 

    public RR(String nombre, int quantum) {
        super(nombre); 
        this.quantum = quantum;
    }

    @Override
    public ResultadoEjecucion ejecutar(ArrayList<BCP> procesos, int tiempoInicio) {
         // Copia para no modificar los procesos reales 
         ArrayList<BCP> procesosCopias = new ArrayList<>();
         for (BCP p : procesos) {
             procesosCopias.add(new BCP(p)); 
         }
        Queue<BCP> cola = new LinkedList<>(); // Cola de procesos listos
        ResultadoEjecucion resultado = new ResultadoEjecucion("RR"); 
        Map<String, List<Integer>> tiemposEjecucion = new HashMap<>(); // Registro de tiempos por proceso
        int tiempo = tiempoInicio; 
        int procesosPendientes = procesosCopias.size(); // Contador de procesos por terminar
        List<BCP> yaAgregados = new ArrayList<>(); // Procesos ya encolados

        while (procesosPendientes > 0) {
            // Agregar procesos que llegaron en el tiempo actual
            for (BCP p : procesosCopias) {
                if (p.getLlegada() <= tiempo && !yaAgregados.contains(p)) {
                    cola.offer(p); // Agregar a la cola de listos
                    yaAgregados.add(p); // Marcar como encolado
                }
            }

            //Avanzar el tiempo si no hay procesos en cola
            if (cola.isEmpty()) {
                tiempo++;
                continue;
            }

            BCP actual = cola.poll();
            List<Integer> tiempos = tiemposEjecucion.getOrDefault(actual.getNombre(), new ArrayList<>());

            // Ejecutar el proceso por el quantum o hasta que termine
            int ejecuciones = Math.min(quantum, actual.getRafagas());
            for (int i = 0; i < ejecuciones; i++) {
                tiempos.add(tiempo++); // Registrar tiempo de ejecucinn
                
                // Verificar si llegan nuevos procesos cuando esta siendo ejecutado
                for (BCP p : procesosCopias) {
                    if (p.getLlegada() == tiempo && !yaAgregados.contains(p)) {
                        cola.offer(p);
                        yaAgregados.add(p);
                    }
                }
            }
            //Actualizar rafags restantes del proceso
            actual.setRafagas(actual.getRafagas() - ejecuciones);
            
            //Volver a agregar a la cola si todavia tiene rafagas restantes 
            if (actual.getRafagas() > 0) {
                cola.offer(actual); 
            } else {
                procesosPendientes--; //Sino se disminuye el contador
            }
            tiemposEjecucion.put(actual.getNombre(), tiempos);
        }

        int totalEspera = 0;
        int totalEjecucion = 0;

        for (BCP p : procesos) {
            List<Integer> t = tiemposEjecucion.get(p.getNombre());

            int inicio = t.get(0); 
            int fin = t.get(t.size() - 1) + 1; 
            int espera = inicio - p.getLlegada(); 
            int ejecucion = fin - p.getLlegada(); 

            totalEspera += espera;
            totalEjecucion += ejecucion;

            resultado.addTiempos(p.getNombre(), t); 
        }
        resultado.setTiempoPromedioEspera(totalEspera / (float) procesos.size());
        resultado.setTiempoPromedioEjecucion(totalEjecucion / (float) procesos.size());

        // Calcular tiempo final de ejecucion
        int tiempoFinal = 0;
        for (List<Integer> tiempos : tiemposEjecucion.values()) {
            for (int t : tiempos) {
                if (t > tiempoFinal) tiempoFinal = t;
            }
        }
        resultado.setFinTiempo(tiempoFinal);

        return resultado;
    }
}
