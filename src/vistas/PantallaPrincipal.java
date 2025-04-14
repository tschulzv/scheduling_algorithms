package vistas;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import modelos.*;
import java.util.List;
import java.util.ArrayList;

public class PantallaPrincipal extends JFrame {

    private final java.util.List<JCheckBox> checkboxes = new ArrayList<>();
    private final int MAX_SELECTION = 3;

    public PantallaPrincipal(List<BCP> procesos) {
        setTitle("Algoritmos de Planificación de Procesos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // convertir la lista de procesos en datos para la tabla
        Object[][] datos = new Object[procesos.size()][3];

        for (int i = 0; i < procesos.size(); i++) {
            BCP proceso = procesos.get(i);
            datos[i][0] = proceso.getNombre();
            datos[i][1] = proceso.getLlegada();
            datos[i][2] = proceso.getRafagas();
        }

        // Panel izquierdo: Tabla de procesos
        String[] columnas = {"Nombre", "T. Llegada", "Rafagas"};
        
        JTable tabla = new JTable(new DefaultTableModel(datos, columnas));
        JScrollPane scrollTabla = new JScrollPane(tabla);
        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setBorder(BorderFactory.createTitledBorder("Procesos Leídos"));
        panelTabla.add(scrollTabla, BorderLayout.CENTER);

        // Panel derecho: Algoritmos
        JPanel panelAlgoritmos = new JPanel();
        panelAlgoritmos.setLayout(new BoxLayout(panelAlgoritmos, BoxLayout.Y_AXIS));
        panelAlgoritmos.setBorder(BorderFactory.createTitledBorder("Algoritmos"));

        JCheckBox fcfs = new JCheckBox("FCFS");
        JCheckBox sjfSin = new JCheckBox("SJF Sin Desalojo");
        JCheckBox sjfCon = new JCheckBox("SJF Con Desalojo");
        JCheckBox prioridad = new JCheckBox("Prioridad");
        JCheckBox rr = new JCheckBox("RR");
        JCheckBox hrrn = new JCheckBox("HRRN");

        checkboxes.add(fcfs);
        checkboxes.add(sjfSin);
        checkboxes.add(sjfCon);
        checkboxes.add(prioridad);
        checkboxes.add(rr);
        checkboxes.add(hrrn);

        // Añadir listeners a cada uno para limitar la selección
        for (JCheckBox cb : checkboxes) {
            cb.addItemListener(e -> {
                long seleccionados = checkboxes.stream().filter(AbstractButton::isSelected).count();
                if (seleccionados > MAX_SELECTION) {
                    cb.setSelected(false);
                    JOptionPane.showMessageDialog(this, "Solo puedes seleccionar hasta 3 algoritmos.");
                }
            });
        }

        JTextField quantumField = new JTextField("4", 5);
        JPanel rrPanel = new JPanel();
        rrPanel.add(rr);
        rrPanel.add(new JLabel("Q ="));
        rrPanel.add(quantumField);

        JButton btnEjecutar = new JButton("Ejecutar");

        panelAlgoritmos.add(fcfs);
        panelAlgoritmos.add(sjfSin);
        panelAlgoritmos.add(sjfCon);
        panelAlgoritmos.add(prioridad);
        panelAlgoritmos.add(rrPanel);
        panelAlgoritmos.add(hrrn);
        panelAlgoritmos.add(Box.createRigidArea(new Dimension(0, 10)));
        panelAlgoritmos.add(btnEjecutar);

        // Agregamos los paneles al frame principal
        add(panelTabla, BorderLayout.WEST);
        add(panelAlgoritmos, BorderLayout.EAST);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
