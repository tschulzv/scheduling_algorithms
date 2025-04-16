package modelos;
import java.util.*;

// Algoritmo de SJF Sin Desalojo
public class SJFExpulsivo extends Algoritmo {
    public SJFExpulsivo(String nombre){
        super(nombre);
    }

    @Override
    public ResultadoEjecucion ejecutar(ArrayList<BCP> procesos){
        int reloj = 0; // lleva la cuenta del tiempo actual
        int totalEjecucion = 0; 
        int totalEspera = 0;

        // crear objeto donde cargaremos los procesos con sus tiempos
        ResultadoEjecucion resultado = new ResultadoEjecucion("SJF Expulsivo");

        // crear una cola de prioridad con los BCP ordenados por cantidad de rafagas
        PriorityQueue<BCP> cola = new PriorityQueue<>(Comparator.comparingInt(BCP::getRafagas));
        
        while (!procesos.isEmpty() || !cola.isEmpty()){
            
            // en cada tiempo, vemos cuales procesos ya 'llegaron' y los ponemos en la cola
            for (BCP p : procesos){
                if (p.getLlegada() <= reloj){
                    cola.add(p);
                    procesos.remove(p);
                }
            }       
            if (!cola.isEmpty()){
                // vemos los procesos en cola actualmente, sacar el de menor cant de rafagas 
                BCP actual = cola.poll();
                actual.setRafagas(actual.getRafagas() - 1); // decrementamos sus rafagas
            }
        }

        /* 
        // ordenar por orden de llegada usando un comparator
        procesos.sort(Comparator.comparingInt(BCP::getRafagas));

        // en este caso iremos reordenando el arraylist multiples veces 
        for (BCP proceso : procesos){
            // agregar su tiempo de espera al total
            int espera = reloj - proceso.getLlegada();
            totalEspera += espera; 
            // agregar tiempo de ejecucion al total
            totalEjecucion = proceso.getRafagas() + espera;

            // crear el arraylist de tiempos donde se ejecuto el proceso
            List<Integer> tiempos = new ArrayList<>();
            // hasta que terminesn las rafagas, ir agregando al arraylist de 'tiempos'
            for (int r = 0; r < proceso.getRafagas(); r++) {
                tiempos.add(reloj);
                reloj++; // aumentar el tiempo actual
            }

            // agregamos el proceso y sus tiempos al resultado
            resultado.addTiempos(proceso.getNombre(), tiempos);
        }*/
        int cantProcesos = procesos.size();
        resultado.setFinTiempo(--reloj); // para saber cuantas columnas poner en la tabla
        resultado.setTiempoPromedioEjecucion(totalEjecucion / (float)cantProcesos);
        resultado.setTiempoPromedioEspera(totalEspera / (float)cantProcesos);

        return resultado;
    }
    
    @Override
    public ResultadoEjecucion ejecutar(ArrayList<BCP> procesos, int quantum){
        System.out.println("ERROR. No se necesita quantum para este algoritmo");
        return null;
    }
}