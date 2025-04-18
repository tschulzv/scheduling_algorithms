package modelos;
import java.util.*;
import java.util.stream.Collectors;

import utilidades.BCPUtils;

// Algoritmo de SJF Sin Desalojo
public class SJF extends Algoritmo {
    public SJF(String nombre){
        super(nombre);
    }

    @Override
    public ResultadoEjecucion ejecutar(ArrayList<BCP> procesos, int tiempoInicio) {
        // Creamos una copia porque vamos a ir quitando procesos
        ArrayList<BCP> copia = BCPUtils.copiarLista(procesos);

        int reloj = tiempoInicio;
        int totalEjecucion = 0;
        int totalEspera = 0;

        ResultadoEjecucion resultado = new ResultadoEjecucion("SJF");

        // Tiempos de ejecución por proceso
        Map<String, List<Integer>> tiemposPorProceso = new HashMap<>();

        while (!copia.isEmpty()) {
            // filtrar procesos que hayan llegado hasta el tiempo actual
            int tiempoActual = reloj;
            List<BCP> disponibles = copia.stream()
                .filter(p -> p.getLlegada() <= tiempoActual)
                .collect(Collectors.toList());

            if (disponibles.isEmpty()) {
                reloj++; // si no hay procesos listos, avanzamos el reloj
                continue;
            }

            // elegir el de menor cantidad de ráfagas
            BCP proceso = Collections.min(disponibles, Comparator.comparingInt(BCP::getRafagas));
            copia.remove(proceso);

            // tiempo de espera = reloj actual - llegada
            int espera = reloj - proceso.getLlegada();
            totalEspera += espera;

            // tiempo total de ejecución = rafagas + espera
            int ejec = espera + proceso.getRafagas();
            totalEjecucion += ejec;

            // guardar los tiempos de ejecución
            List<Integer> tiempos = new ArrayList<>();
            for (int i = 0; i < proceso.getRafagas(); i++) {
                tiempos.add(reloj);
                reloj++;
            }
            tiemposPorProceso.put(proceso.getNombre(), tiempos);
        }

        int cantProcesos = procesos.size();
        resultado.setFinTiempo(reloj - 1);
        resultado.setTiempoPromedioEjecucion(totalEjecucion / (float) cantProcesos);
        resultado.setTiempoPromedioEspera(totalEspera / (float) cantProcesos);
        resultado.setTiemposPorProceso(tiemposPorProceso);

        return resultado;
    }

}