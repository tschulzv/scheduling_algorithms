package modelos;
import java.util.*;

public class FCFS extends Algoritmo {
    public FCFS(String nombre){
        super(nombre);
    }

    @Override
    public ResultadoEjecucion ejecutar(ArrayList<BCP> procesos, int tiempoInicio){
        // ordenar por llegada usando un comparator
        procesos.sort(Comparator.comparingInt(BCP::getLlegada));

        int reloj = tiempoInicio; // lleva la cuenta del tiempo actual
        int totalEjecucion = 0; 
        int totalEspera = 0;

        // crear objeto donde cargaremos los procesos con sus tiempos
        ResultadoEjecucion resultado = new ResultadoEjecucion("FCFS");

        for (BCP proceso : procesos){
            // agregar su tiempo de espera al total
            int espera = reloj - proceso.getLlegada();
            totalEspera += espera; 
            // agregar tiempo de ejecucion al total
            totalEjecucion += proceso.getRafagas() + espera;

            // crear el arraylist de tiempos donde se ejecuto el proceso
            List<Integer> tiempos = new ArrayList<>();
            // hasta que terminesn las rafagas, ir agregando al arraylist de 'tiempos'
            for (int r = 0; r < proceso.getRafagas(); r++) {
                tiempos.add(reloj);
                reloj++; // aumentar el tiempo actual
            }

            // agregamos el proceso y sus tiempos al resultado
            resultado.addTiempos(proceso.getNombre(), tiempos);
        }
        int cantProcesos = procesos.size();
        resultado.setTiempoPromedioEjecucion(totalEjecucion / (float)cantProcesos);
        resultado.setTiempoPromedioEspera(totalEspera / (float)cantProcesos);
        resultado.setFinTiempo(--reloj); // para saber cuantas columnas poner en la tabla
        return resultado;
    }
    
}