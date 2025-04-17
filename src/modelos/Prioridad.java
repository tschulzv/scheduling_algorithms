package modelos;
import java.util.*;

public class Prioridad extends Algoritmo {
    public Prioridad(String nombre) {
        super(nombre);
    }
    @Override
    public ResultadoEjecucion ejecutar(ArrayList<BCP> procesos, int tiempoInicio) {
        PriorityQueue<BCP> cola = new PriorityQueue<>(Comparator.comparingInt(BCP::getPrioridad));
        Map<String, List<Integer>> tiemposEjecucion = new HashMap<>();
        Set<String> encolados = new HashSet<>(); // Para no encolar dos veces
        Set<String> ejecutados = new HashSet<>(); // Para asegurar conteo real
        int tiempo = tiempoInicio;
        int totalEspera = 0;
        int totalEjecucion = 0;
        ResultadoEjecucion resultado = new ResultadoEjecucion("Prioridad");

        while (ejecutados.size() < procesos.size()) {
            for (BCP p : procesos) {
                if (p.getLlegada() <= tiempo && !encolados.contains(p.getNombre())) {
                    cola.offer(p);
                    encolados.add(p.getNombre());
                }
            }

            if (!cola.isEmpty()) {
                BCP actual = cola.poll();
                if (ejecutados.contains(actual.getNombre())) continue;

                List<Integer> tiempos = new ArrayList<>();
                for (int i = 0; i < actual.getRafagas(); i++) {
                    tiempos.add(tiempo++);
                }

                tiemposEjecucion.put(actual.getNombre(), tiempos);
                ejecutados.add(actual.getNombre());

                int espera = tiempos.get(0) - actual.getLlegada();
                int ejecucion = tiempos.get(tiempos.size() - 1) - actual.getLlegada() + 1;
                totalEspera += espera;
                totalEjecucion += ejecucion;
            } else {
                tiempo++; 
            }
        }

        for (BCP p : procesos) {
            resultado.addTiempos(p.getNombre(), tiemposEjecucion.getOrDefault(p.getNombre(), new ArrayList<>()));
        }

        resultado.setFinTiempo(tiempo);
        resultado.setTiempoPromedioEspera((float) totalEspera / procesos.size());
        resultado.setTiempoPromedioEjecucion((float) totalEjecucion / procesos.size());

        return resultado;
    }

}
