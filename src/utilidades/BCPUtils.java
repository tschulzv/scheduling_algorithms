package utilidades;
import java.util.ArrayList;
import modelos.*;

public class BCPUtils {
    /*  recibe un arraylist de BCPs y crea una deep copy de ellos
    Utilizado en algoritmos expulsivos, de modo que se pueda decrementar los números de los BCP
    sin modificar al BCP original */
    public static ArrayList<BCP> copiarLista(ArrayList<BCP> original) {
        ArrayList<BCP> copia = new ArrayList<>();
        for (BCP proceso : original) {
            copia.add(new BCP(proceso)); // llama al constructor copia de BCP 
        }
        return copia;
    }
    
}
