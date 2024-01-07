/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class JuegoMecanografiaFrame extends JFrame {

    private static final String LETRAS_JUGADOR_1 = "qwertasdfzxcv";
    private static final String LETRAS_JUGADOR_2 = "yuuiopghjklbnm";
    private static final int NUM_INTENTOS = 5;
    private static final int TIEMPO_AVANCE_LETRA = 1500; // Tiempo en milisegundos
    private static final int NUM_LETRAS_A_GENERAR = 10;

    private JLabel letraLabel;
    private JTextField entradaField;
    private JLabel resultadoLabel;

    private int intentosRestantes = NUM_INTENTOS;
    private int puntajeTotal = 0;
    private int currentIndex = 0;
    private int letrasGeneradas = 0;
    private long tiempoInicio = 0;
    private Timer timer;

    private boolean turnoJugador1 = true;
    private boolean inicioJuego = false;

    public JuegoMecanografiaFrame() {
        super("Juego de Mecanografía");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 250);
        setLayout(new GridLayout(4, 1));

        JButton iniciarButton = new JButton("Iniciar Juego");
        iniciarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                inicioJuego = true;
                iniciarJuego();
            }
        });
        add(iniciarButton);

        letraLabel = new JLabel();
        letraLabel.setHorizontalAlignment(JLabel.CENTER);
        add(letraLabel);

        entradaField = new JTextField();
        entradaField.setHorizontalAlignment(JTextField.CENTER);
        entradaField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (inicioJuego && e.getKeyCode() != KeyEvent.VK_BACK_SPACE && e.getKeyCode() != KeyEvent.VK_DELETE) {
                    avanzarLetra();
                }
            }
        });
        add(entradaField);

        resultadoLabel = new JLabel();
        resultadoLabel.setHorizontalAlignment(JLabel.CENTER);
        add(resultadoLabel);

        letraLabel.setVisible(false);
        entradaField.setVisible(false);
        resultadoLabel.setVisible(false);

        timer = new Timer(TIEMPO_AVANCE_LETRA, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                avanzarLetra();
            }
        });
    }

    private void iniciarJuego() {
        if (turnoJugador1) {
            JOptionPane.showMessageDialog(this, "¡Turno del Jugador 1!", "Inicio Jugador 1", JOptionPane.INFORMATION_MESSAGE);
            turnoJugador1 = false;
            iniciarTurno(LETRAS_JUGADOR_1);
        } else {
            JOptionPane.showMessageDialog(this, "¡Turno del Jugador 2!", "Inicio Jugador 2", JOptionPane.INFORMATION_MESSAGE);
            turnoJugador1 = true;
            iniciarTurno(LETRAS_JUGADOR_2);
        }
    }

    private void iniciarTurno(String letras) {
        letraLabel.setVisible(true);
        entradaField.setVisible(true);
        resultadoLabel.setVisible(true);

        letrasGeneradas = 0;
        currentIndex = 0;
        puntajeTotal = 0;
        mostrarSiguienteLetra(letras);
        tiempoInicio = System.currentTimeMillis();
    }

    private void mostrarSiguienteLetra(String letras) {
        if (letrasGeneradas < NUM_LETRAS_A_GENERAR) {
            if (currentIndex < letras.length()) {
                letraLabel.setText("Escribe la letra: '" + letras.charAt(currentIndex) + "'");
                entradaField.setText("");
                entradaField.requestFocus();
            }
        } else {
            timer.stop();
            long tiempoFinal = System.currentTimeMillis();
            double tiempoTotal = (tiempoFinal - tiempoInicio) / 1000.0; // Tiempo en segundos

            String resumen = turnoJugador1 ? "Jugador 1" : "Jugador 2";
            JOptionPane.showMessageDialog(this,
                    "Juego terminado para " + resumen + "\n" +
                            "Letras correctas: " + puntajeTotal + "\n" +
                            "Letras incorrectas: " + (NUM_LETRAS_A_GENERAR - puntajeTotal) + "\n" +
                            "Tiempo total: " + tiempoTotal + " segundos",
                    "Resumen", JOptionPane.INFORMATION_MESSAGE);

            if (!turnoJugador1) {
                JOptionPane.showMessageDialog(this, "Fin del Juego", "Fin del Juego", JOptionPane.INFORMATION_MESSAGE);
                turnoJugador1 = true;
                inicioJuego = false;
            } else {
                iniciarJuego();
            }
        }
    }

    private void avanzarLetra() {
        if (letrasGeneradas < NUM_LETRAS_A_GENERAR) {
            String letras = turnoJugador1 ? LETRAS_JUGADOR_1 : LETRAS_JUGADOR_2;
            if (currentIndex < letras.length()) {
                char letra = letras.charAt(currentIndex);
                char inputChar = entradaField.getText().charAt(0);

                if (inputChar == letra) {
                    puntajeTotal++;
                }

                currentIndex++;
                letrasGeneradas++;
                mostrarSiguienteLetra(letras);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JuegoMecanografiaFrame frame = new JuegoMecanografiaFrame();
            frame.setVisible(true);
        });
    }
}
