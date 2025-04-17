package modelos;
import java.util.*;

public class HRRN extends Algoritmo {
    
    public HRRN(String nombre) {
        super(nombre);
    }

    @Override
    public ResultadoEjecucion ejecutar(ArrayList<BCP> procesos, int tiempoInicio) {
        // Copia para no modificar los procesos reales         
        ArrayList<BCP> procesosCopia = new ArrayList<>();
        for (BCP p : procesos) {
            procesosCopia.add(new BCP(p));
        }
        
        ResultadoEjecucion resultado = new ResultadoEjecucion("HRRN");
        List<BCP> colaListos = new ArrayList<>();
        List<BCP> procesosTerminados = new ArrayList<>();
        Map<String, List<Integer>> tiemposEjecucion = new HashMap<>();
        int tiempoActual = tiempoInicio;
        
        // Inicializar tiempos de ejecucion para todos los procesos
        for (BCP p : procesosCopia) {
            tiemposEjecucion.put(p.getNombre(), new ArrayList<>());
        }
        
        while (procesosTerminados.size() < procesosCopia.size()) {
            //Agregar procesos que han llegado al tiempo actual
            for (BCP p : procesosCopia) {
                if (p.getLlegada() <= tiempoActual && !colaListos.contains(p) && !procesosTerminados.contains(p)) {
                    colaListos.add(p);
                }
            }
            if (colaListos.isEmpty()) {
                tiempoActual++;
                continue;
            }
            //Calcular Response Ratio para cada proceso en cola
            BCP procesoAEjecutar = null;
            double maxResponseRatio = -1;
            
            for (BCP p : colaListos) {
                int tiempoEspera = tiempoActual - p.getLlegada();
                double responseRatio = (tiempoEspera + p.getRafagas()) / (double) p.getRafagas();
                
                if (responseRatio > maxResponseRatio) {
                    maxResponseRatio = responseRatio;
                    procesoAEjecutar = p;
                }
            }
            colaListos.remove(procesoAEjecutar);
            List<Integer> tiempos = tiemposEjecucion.get(procesoAEjecutar.getNombre());
            
            for (int i = 0; i < procesoAEjecutar.getRafagas(); i++) {
                tiempos.add(tiempoActual++);
            }
            procesosTerminados.add(procesoAEjecutar);
            
            //Mientras se ejecuta, verificar si llegan nuevos procesos
            for (BCP p : procesosCopia) {
                if (p.getLlegada() <= tiempoActual && !colaListos.contains(p) && !procesosTerminados.contains(p)) {
                    colaListos.add(p);
                }
            }
        }
        int totalEspera = 0;
        int totalEjecucion = 0;
        int tiempoFinal = 0;
        
        for (BCP p : procesos) {
            List<Integer> t = tiemposEjecucion.get(p.getNombre());
            
            if (t == null || t.isEmpty()) continue;
            
            int inicio = t.get(0);
            int fin = t.get(t.size() - 1);
            int espera = inicio - p.getLlegada();
            int ejecucion = fin - p.getLlegada();
            
            totalEspera += espera;
            totalEjecucion += ejecucion;
            
            if (fin > tiempoFinal) {
                tiempoFinal = fin;
            }
            
            resultado.addTiempos(p.getNombre(), t);
        }
        
        resultado.setTiempoPromedioEspera(totalEspera / (float) procesos.size());
        resultado.setTiempoPromedioEjecucion(totalEjecucion / (float) procesos.size());
        resultado.setFinTiempo(tiempoFinal);
        
        return resultado;
    }
}
