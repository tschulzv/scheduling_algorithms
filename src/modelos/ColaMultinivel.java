package modelos;

import java.util.*;

// esta clase no extiende de 'Algoritmo', porque simplemente ejecuta los demás algoritmos
public class ColaMultinivel {
    
    public ResultadoEjecucion ejecutarMultinivel(ArrayList<BCP> procesos, Algoritmo algo1, Algoritmo algo2, Algoritmo algo3) {
        ArrayList<BCP> prioridad1 = new ArrayList<>();
        ArrayList<BCP> prioridad2 = new ArrayList<>();
        ArrayList<BCP> prioridad3 = new ArrayList<>();
        int reloj = 0;
        
        // seperar los procesos en prioridades
        for (BCP p : procesos) {
            switch (p.getPrioridad()) {
                case 1 -> prioridad1.add(p);
                case 2 -> prioridad2.add(p);
                case 3 -> prioridad3.add(p);
            }
        }

        // ejecutar cada algoritmo por separado
        ResultadoEjecucion resul1 = algo1.ejecutar(prioridad1, reloj);
        // obetener el 'tiempo actual' al terminar algorimto 1
        reloj = resul1.getFinTiempo();
        ResultadoEjecucion resul2 = algo2.ejecutar(prioridad2, reloj);
        // obetener el 'tiempo actual' al terminar algoritmo 2
        reloj = resul2.getFinTiempo();
        ResultadoEjecucion resul3 = algo2.ejecutar(prioridad3, reloj);
        // combinar los resultados
        ResultadoEjecucion resulFinal = ResultadoEjecucion.mergeResultados(resul1, resul2, resul3);

        return resulFinal;
    }
    
}
