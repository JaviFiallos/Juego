package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class JuegoMecanografiaFacil {

    private final int CANTIDAD_RONDAS = 5;

    private JFrame ventana;
    private JLabel letra;
    private JButton boton;
    private Timer cronometro;
    private Map<Integer, Long> tiemposPorJugador;
    private int[] puntuacion;
    private long tiempoInicio;
    private char letraActual;
    private int rondaActual;
    private int jugadorActual;
    private List<String> nombresJugadores;

    public JuegoMecanografiaFacil(List<String> nombresJugadores) {
        this.nombresJugadores = nombresJugadores;

        ventana = new JFrame("Juego de Mecanografía");
        ventana.setSize(400, 300);
        ventana.setLayout(new BorderLayout());

        letra = new JLabel("", SwingConstants.CENTER);
        letra.setFont(new Font("Arial", Font.BOLD, 100));
        ventana.add(letra, BorderLayout.CENTER);

        boton = new JButton("Iniciar " + nombresJugadores.get(0));
        boton.addActionListener(e -> iniciarReiniciar());
        ventana.add(boton, BorderLayout.SOUTH);

        ventana.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                comprobarLetra(e.getKeyChar());
            }
        });

        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setVisible(true);
    }

    private void iniciarJuego() {
        tiemposPorJugador = new HashMap<>();
        puntuacion = new int[2];
        jugadorActual = 0;
        puntuacion[0] = 0;
        puntuacion[1] = 0;
        rondaActual = 0;
        tiempoInicio = System.currentTimeMillis();
        generarLetraAleatoria();
        cronometro = new Timer(10, e -> {
            if (rondaActual > CANTIDAD_RONDAS)
                finalizarJuego();
        });
        cronometro.start();
        ventana.requestFocus();
    }

    private void reiniciarJuego() {
        cronometro.stop();
        puntuacion[0] = 0;
        puntuacion[1] = 0;
        rondaActual = 0;
        letra.setText("");
        jugadorActual = 0;
        boton.setText("Iniciar " + nombresJugadores.get(0));
        tiemposPorJugador.clear();
        ventana.requestFocus();
    }

    private void iniciarReiniciar() {
        if (boton.getText().startsWith("Iniciar")) {
            iniciarJuego();
            if (nombresJugadores.size() >= 2) {
                boton.setText("Turno " + nombresJugadores.get(1));
            }
        } else if (boton.getText().startsWith("Turno")) {
            if (nombresJugadores.size() >= 2) {
                jugadorActual = 1;
                generarLetraAleatoria();
                ventana.requestFocusInWindow();
                boton.setText("Reiniciar Partida");
            }
        } else {
            reiniciarJuego();
            boton.setText("Iniciar " + nombresJugadores.get(0));
        }
    }

    private void finalizarJuego() {
        cronometro.stop();
        boton.setText("Iniciar " + nombresJugadores.get(0));
        long tiempoFinal = System.currentTimeMillis() - tiempoInicio;

        String mensajeGanador;
        int ganador = tiemposPorJugador.getOrDefault(0, Long.MAX_VALUE) <
                      tiemposPorJugador.getOrDefault(1, Long.MAX_VALUE) ? 0 : 1;

        if (tiemposPorJugador.getOrDefault(0, Long.MAX_VALUE)
                .equals(tiemposPorJugador.getOrDefault(1, Long.MAX_VALUE))) {
            mensajeGanador = "¡Empate!";
        } else {
            mensajeGanador = "¡" + nombresJugadores.get(ganador) + " es el ganador!";
        }

        mostrarResultados(tiempoFinal, mensajeGanador);
    }

    private void mostrarResultados(long tiempoFinal, String mensajeGanador) {
    JPanel resultadosPanel = new JPanel();
    resultadosPanel.setLayout(new BoxLayout(resultadosPanel, BoxLayout.Y_AXIS));

    resultadosPanel.add(new JLabel(nombresJugadores.get(0) + " - Aciertos: " + puntuacion[0] +
            " - Tiempo: " + obtenerTiempoJugador(0) + "s"));
    resultadosPanel.add(new JLabel(nombresJugadores.get(1) + " - Aciertos: " + puntuacion[1] +
            " - Tiempo: " + obtenerTiempoJugador(1) + "s"));
    resultadosPanel.add(new JLabel("Tiempo total: " + tiempoFinal / 1000.0 + "s"));
    resultadosPanel.add(new JLabel(mensajeGanador));

    JButton reiniciarButton = new JButton("Reiniciar");
    reiniciarButton.addActionListener(e -> reiniciarJuego());
    resultadosPanel.add(reiniciarButton);

    ventana.getContentPane().removeAll();
    ventana.getContentPane().add(resultadosPanel);
    
    // Añadir la acción para que el botón "Reiniciar" funcione correctamente
    reiniciarButton.addActionListener(e -> {
        reiniciarJuego();
        ventana.getContentPane().removeAll();
        ventana.getContentPane().add(boton, BorderLayout.SOUTH);
        ventana.getContentPane().add(letra, BorderLayout.CENTER);
        ventana.revalidate();
        ventana.repaint();
    });

    ventana.revalidate();
}

    private double obtenerTiempoJugador(int jugador) {
        Long tiempo = tiemposPorJugador.get(jugador);
        return tiempo != null ? tiempo / 1000.0 : 0.0;
    }

    private void comprobarLetra(char letra) {
        if (cronometro.isRunning()) {
            if (letra == letraActual) {
                puntuacion[jugadorActual]++;
            } else {
                tiemposPorJugador.put(jugadorActual, tiemposPorJugador.getOrDefault(jugadorActual, 0L) + 500);
            }

            if (rondaActual >= CANTIDAD_RONDAS) {
                cambiarTurno();
            } else {
                generarLetraAleatoria();
            }
        }
    }

    private void cambiarTurno() {
        if (nombresJugadores.size() >= 2) {
            if (jugadorActual == 0) {
                jugadorActual = 1;
                rondaActual = 0;
                boton.setText("Turno " + nombresJugadores.get(1));
            } else {
                finalizarJuego();
            }
            letra.setText("");
        }
    }

    private void generarLetraAleatoria() {
        Random r = new Random();
        char letraGenerada;

        if (jugadorActual == 0) {
            char[] letrasJugador1 = {'q', 'w', 'e', 'r', 't', 'a', 's', 'd', 'f', 'z', 'x', 'c', 'v'};
            letraGenerada = letrasJugador1[r.nextInt(letrasJugador1.length)];
        } else {
            char[] letrasJugador2 = {'y', 'u', 'i', 'o', 'p', 'g', 'h', 'j', 'k', 'l', 'b', 'n', 'm'};
            letraGenerada = letrasJugador2[r.nextInt(letrasJugador2.length)];
        }

        letraActual = letraGenerada;
        letra.setText(String.valueOf(letraActual));
        rondaActual++;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ConfiguracionJuego configuracion = new ConfiguracionJuego();
            List<String> nombresJugadores = configuracion.getNombresJugadores();
            if (nombresJugadores.size() >= 2) {
                new JuegoMecanografiaFacil(nombresJugadores);
            } else {
                JOptionPane.showMessageDialog(null, "Se necesitan al menos dos jugadores para iniciar el juego.");
            }
        });
    }
}
