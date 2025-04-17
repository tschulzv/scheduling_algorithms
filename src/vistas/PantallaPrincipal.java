package vistas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import modelos.*;
import utilidades.*;
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
        JCheckBox colaMulti = new JCheckBox("Cola Multinivel");

        checkboxes.add(fcfs);
        checkboxes.add(sjfSin);
        checkboxes.add(sjfCon);
        checkboxes.add(prioridad);
        checkboxes.add(rr);
        checkboxes.add(hrrn);
        checkboxes.add(colaMulti);

        // item listener para evitar que se seleccionen más de 3 algoritmos
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

        String[] algoritmos = {"FCFS", "SJF Sin Desalojo", "SJF Con Desalojo", "Prioridad", "RR", "HRRN"};

        // algoritmos y quantum para Cola 1
        JComboBox<String> comboNivel1 = new JComboBox<>(algoritmos);
        JTextField quantum1Field = new JTextField("4", 5);
        JPanel panelQuantum1 = new JPanel();
        panelQuantum1.add(new JLabel("Quantum:"));
        panelQuantum1.add(quantum1Field);
        panelQuantum1.setVisible(false);

        // algoritmos y quantum para Cola 2
        JComboBox<String> comboNivel2 = new JComboBox<>(algoritmos);
        JTextField quantum2Field = new JTextField("4", 5);
        JPanel panelQuantum2 = new JPanel();
        panelQuantum2.add(new JLabel("Quantum:"));
        panelQuantum2.add(quantum2Field);
        panelQuantum2.setVisible(false);

        // algoritmos y quantum para Cola 3
        JComboBox<String> comboNivel3 = new JComboBox<>(algoritmos);
        JTextField quantum3Field = new JTextField("4", 5);
        JPanel panelQuantum3 = new JPanel();
        panelQuantum3.add(new JLabel("Quantum:"));
        panelQuantum3.add(quantum3Field);
        panelQuantum3.setVisible(false);

        // item listeners para mostrar input de quantum si se selecciona RR en alguna cola
        comboNivel1.addItemListener(e -> {
            panelQuantum1.setVisible(comboNivel1.getSelectedItem().equals("RR"));
        });
        
        comboNivel2.addItemListener(e -> {
            panelQuantum2.setVisible(comboNivel2.getSelectedItem().equals("RR"));
        });
        
        comboNivel3.addItemListener(e -> {
            panelQuantum3.setVisible(comboNivel3.getSelectedItem().equals("RR"));
        });
        
        // panel para la cola multinicel
        JPanel panelColas = new JPanel();
        panelColas.setLayout(new GridLayout(6, 2, 5, 5));
        panelColas.setBorder(BorderFactory.createTitledBorder("Algoritmos Cola Multinivel"));

        panelColas.add(new JLabel("Cola 1 (Prio. Alta):"));
        panelColas.add(comboNivel1);
        panelColas.add(new JLabel(""));
        panelColas.add(panelQuantum1);

        panelColas.add(new JLabel("Cola 2 (Prio. Media):"));
        panelColas.add(comboNivel2);
        panelColas.add(new JLabel(""));
        panelColas.add(panelQuantum2);

        panelColas.add(new JLabel("Cola 3 (Prio. Baja):"));
        panelColas.add(comboNivel3);
        panelColas.add(new JLabel(""));
        panelColas.add(panelQuantum3);

        JButton btnEjecutar = new JButton("Ejecutar");

        panelAlgoritmos.add(fcfs);
        panelAlgoritmos.add(sjfSin);
        panelAlgoritmos.add(sjfCon);
        panelAlgoritmos.add(prioridad);
        panelAlgoritmos.add(rrPanel);
        panelAlgoritmos.add(hrrn);
        panelAlgoritmos.add(colaMulti);
        panelAlgoritmos.add(panelColas);
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
                            resultado = new FCFS("FCFS").ejecutar(procesos, 0);
                            break;
                        case "SJF Sin Desalojo":
                            resultado = new SJF("SJF").ejecutar(procesos, 0);
                            break;
                        case "SJF Con Desalojo":
                            resultado = new SJFExpulsivo("SJF Con Desalojo").ejecutar(procesos, 0);
                            break;
                        case "Prioridad":
                            resultado = new Prioridad("Prioridad").ejecutar(procesos, 0);
                            break;
                        case "RR":
                            int quantum = Integer.parseInt(quantumField.getText());
                            resultado = new RR("RR", quantum).ejecutar(procesos, 0);
                            break;
                        case "HRRN":
                            resultado = new HRRN("HRRN").ejecutar(procesos,0);
                            break;
                        case "Cola Multinivel":
                            String alg1 = (String) comboNivel1.getSelectedItem();
                            String alg2 = (String) comboNivel2.getSelectedItem();
                            String alg3 = (String) comboNivel3.getSelectedItem();
                            
                            int q1 = 0, q2 = 0, q3 = 0;
                            
                            try {
                                if (alg1.equals("RR")) q1 = Integer.parseInt(quantum1Field.getText());
                                if (alg2.equals("RR")) q2 = Integer.parseInt(quantum2Field.getText());
                                if (alg3.equals("RR")) q3 = Integer.parseInt(quantum3Field.getText());
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(this, "Quantum inválido en alguna cola.");
                                return;
                            }
                            Algoritmo a1 = crearAlgoritmo(alg1, q1);
                            Algoritmo a2 = crearAlgoritmo(alg2, q2);
                            Algoritmo a3 = crearAlgoritmo(alg3, q3);
                            resultado = new ColaMultinivel().ejecutarMultinivel(procesos, a1, a2, a3);
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

    // funcion auxiliar para crear los algoritmos segun lo seleccionado para la cola multinivel
    private static Algoritmo crearAlgoritmo(String nombre, int quantum) {
        switch (nombre) {
            case "FCFS":
                return new FCFS(nombre);
            case "SJF Sin Desalojo":
                return new SJF(nombre);
            case "SJF Con Desalojo":
                return new SJFExpulsivo(nombre);
            case "RR":
                //return new RoundRobin(nombre, quantum);
            case "HRRN":
                //return new HRRN(nombre);
            case "Prioridad":
                //return new Prioridad(nombre); // si la tenés implementada
            default:
                return null;
        }
    }
    

}
