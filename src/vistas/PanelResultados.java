package vistas;

import modelos.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;

public class PanelResultados {
    public PanelResultados(ResultadoEjecucion resultado){
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(resultado.getNombreAlgoritmo()));
        
        // del ResultadoEjecucion, obtenemos los encabezados y la matriz
        JTable tabla = new JTable(new DefaultTableModel(resultado.getMatriz(), resultado.getEncabezados()));
    }
}
