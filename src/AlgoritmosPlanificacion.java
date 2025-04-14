import java.util.*;
import modelos.*;
import vistas.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Tania Schulz
 */
public class AlgoritmosPlanificacion {

    public static void main(String[] args) {
        // si no se le pasa un nombre de archivo como args, usa ejemplo.txt
        String archivo =  args.length == 1 ? args[0] : "ejemplo.txt";
        List<BCP> procesos = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            // leer linea a linea
            String line;
            while ((line = br.readLine()) != null) {
                // separar por comas las partes del proceso
                String[] datosProceso = line.split(",");
                // crear el objeto bcp
                BCP proceso = new BCP(datosProceso[0], Integer.parseInt(datosProceso[1]), Integer.parseInt(datosProceso[2]), Integer.parseInt(datosProceso[3]));
                // agregar a la lista
                procesos.add(proceso);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // leer el csv y crear los procesos
        PantallaPrincipal pantalla = new PantallaPrincipal(procesos);

    }
}