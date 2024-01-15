package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ConfiguracionJuego extends JFrame {

    private List<String> nombresJugadores;
    private DefaultTableModel tableModel;

    public ConfiguracionJuego() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private void initComponents() {
        JLabel label = new JLabel("Configuración de Jugadores");
        JTextField jugadorTextField = new JTextField();
        JButton agregarJugadorButton = new JButton("Agregar Jugador");
        JButton regresarButton = new JButton("Regresar");

        nombresJugadores = new ArrayList<>();

        // Tabla para mostrar los nombres de los jugadores
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Nombres de Jugadores");
        JTable tablaJugadores = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tablaJugadores);

        agregarJugadorButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        String jugador = jugadorTextField.getText().trim();

        if (!jugador.isEmpty()) {
            nombresJugadores.add(jugador);
            actualizarTabla();
        }

        jugadorTextField.setText("");
    }
});

regresarButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        dispose(); // Cerrar la ventana de configuración

        // Crear una instancia de JuegoMecanografiaFacil con la lista de nombres de jugadores
        new JuegoMecanografiaFacil(nombresJugadores);

        // Volver a la ventana de inicio
        new Inicio();
    }
});

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                        .addComponent(label)
                                        .addComponent(jugadorTextField, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(agregarJugadorButton)
                                        .addComponent(scrollPane)
                                        .addComponent(regresarButton)
                                )
                                .addContainerGap(10, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(label)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jugadorTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(agregarJugadorButton)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(regresarButton)
                                .addContainerGap(10, Short.MAX_VALUE))
        );

        pack();
    }

    private void actualizarTabla() {
        // Limpiar la tabla
        tableModel.setRowCount(0);

        // Agregar los nombres a la tabla
        for (String jugador : nombresJugadores) {
            tableModel.addRow(new Object[]{jugador});
        }
    }

    public List<String> getNombresJugadores() {
        return nombresJugadores;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ConfiguracionJuego());
    }
}
