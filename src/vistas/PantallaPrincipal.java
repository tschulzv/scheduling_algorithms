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
    private final JPanel panelResultados = new JPanel(); // panel pata mostrar las tablas de resultados

    public PantallaPrincipal(ArrayList<BCP> procesos) {
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

        // Panel resultados como atributo
        panelResultados.setLayout(new BoxLayout(panelResultados, BoxLayout.Y_AXIS));
        panelResultados.setBorder(BorderFactory.createTitledBorder("Resultados"));
        JScrollPane scrollResultados = new JScrollPane(panelResultados);

        // Agregar al frame
        add(panelTabla, BorderLayout.WEST);
        add(panelAlgoritmos, BorderLayout.EAST);
        add(scrollResultados, BorderLayout.CENTER);

        // Acción del botón Ejecutar
        btnEjecutar.addActionListener(e -> {
            panelResultados.removeAll(); // Limpiar resultados anteriores

            for (JCheckBox cb : checkboxes) {
                if (cb.isSelected()) {
                    ResultadoEjecucion resultado = null;
                    String nombreAlgoritmo = cb.getText();

                    switch (nombreAlgoritmo) {
                        case "FCFS":
                            resultado = new FCFS("FCFS").ejecutar(procesos);
                            break;
                        case "SJF Sin Desalojo":
                            resultado = new SJF("SJF").ejecutar(procesos);
                            break;
                        case "SJF Con Desalojo":
                            // resultado = new AlgoritmoSJF(true).ejecutar(procesos);
                            break;
                        case "Prioridad":
                            // resultado = new AlgoritmoPrioridad().ejecutar(procesos);
                            break;
                        case "RR":
                            // int quantum = Integer.parseInt(quantumField.getText());
                            // resultado = new AlgoritmoRR(quantum).ejecutar(procesos);
                            break;
                        case "HRRN":
                            // resultado = new AlgoritmoHRRN().ejecutar(procesos);
                            break;
                    }

                    if (resultado != null) {
                        String[] columnasResult = resultado.getEncabezados();
                        Object[][] datosResult = resultado.getMatriz();
                        JTable tablaResultado = new JTable(datosResult, columnasResult);
                        tablaResultado.setRowHeight(25);

                        JPanel panelAlg = new JPanel(new BorderLayout());
                        panelAlg.setBorder(BorderFactory.createTitledBorder(nombreAlgoritmo));
                        panelAlg.add(new JScrollPane(tablaResultado), BorderLayout.CENTER);

                        JPanel panelInfo = new JPanel(new GridLayout(2, 1));
                        panelInfo.add(new JLabel("Promedio de Ejecución: " + String.format("%.2f", resultado.getTiempoPromedioEjecucion())));
                        panelInfo.add(new JLabel("Promedio de Espera: " + String.format("%.2f", resultado.getTiempoPromedioEspera())));
                        panelAlg.add(panelInfo, BorderLayout.SOUTH);

                        panelResultados.add(panelAlg);
                    }
                }
            }

            panelResultados.revalidate();
            panelResultados.repaint();
        });

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
