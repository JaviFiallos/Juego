/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

/**
 *
 * @author Bryan
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class JuegoMecanografiaDificil {

    // cantidad de letras que se mostrarán en la partida
    private final int CANTIDAD_RONDAS = 20;

    // atributos
    private JFrame ventana;
    private JLabel letra;
    private JButton boton;
    private Timer cronometro;
    private Map<Integer, Long> tiemposPorJugador; // Mapa para los tiempos de los jugadores
    private int[] puntuacion; // Array para puntuaciones de los jugadores
    private long tiempoInicio;
    private char letraActual;
    private int rondaActual;
    private int jugadorActual; // Identificador del jugador actual

    // constructor
    public JuegoMecanografiaDificil() {
        // datos ventana
        ventana = new JFrame("Juego de Mecanografía");
        ventana.setSize(400, 300);
        ventana.setLayout(new BorderLayout());
        ventana.setLocationRelativeTo(null);

        // letra
        letra = new JLabel("", SwingConstants.CENTER);
        letra.setFont(new Font("Arial", Font.BOLD, 100));
        ventana.add(letra, BorderLayout.CENTER);

        // botón
        boton = new JButton("Iniciar Jugador 1");
        boton.addActionListener(e -> iniciarReiniciar());
        ventana.add(boton, BorderLayout.SOUTH);

        // evento al pulsar tecla
        ventana.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                comprobarLetra(e.getKeyChar());
            }
        });

        ventana.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                mostrarVentanaAnterior();
                ventana.dispose();
            }
        });
        
        // visibilidad y cierre
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setVisible(true);
    }

    // iniciamos la partida
    public void iniciarJuego() {
        tiemposPorJugador = new HashMap<>(); // Inicialización de los tiempos por jugador
        puntuacion = new int[2]; // Inicialización de las puntuaciones para los dos jugadores
        jugadorActual = 0; // Empezamos con el jugador 1
        puntuacion[0] = 0; // Puntuación inicial del jugador 1
        puntuacion[1] = 0; // Puntuación inicial del jugador 2
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

    // reiniciamos datos y paramos todo
    public void reiniciarJuego() {
        cronometro.stop();
        puntuacion[0] = 0;
        puntuacion[1] = 0;
        rondaActual = 0;
        letra.setText("");
        jugadorActual = 0;
        boton.setText("Iniciar Jugador 1");
        tiemposPorJugador.clear(); // Limpiamos los tiempos por jugador
        ventana.requestFocus();
        
    }

    // alternar entre iniciar y reiniciar
    public void iniciarReiniciar() {
        if (boton.getText().equals("Iniciar Jugador 1")) {
            iniciarJuego();
            boton.setText("Turno Jugador 2");
        } else if (boton.getText().equals("Turno Jugador 2")) {
            jugadorActual = 1; // Cambiamos al jugador 2
            generarLetraAleatoria();
            ventana.requestFocusInWindow(); // Establecer foco en la ventana
            boton.setText("Reiniciar Partida");
        } else {
            reiniciarJuego();
            boton.setText("Iniciar Jugador 1");
        }
    }

    // método que se modifica para determinar al ganador
    public void finalizarJuego() {
        // finalizamos partida
        cronometro.stop();
        boton.setText("Iniciar Jugador 1");
        long tiempoFinal = System.currentTimeMillis() - tiempoInicio;

        // comparación de tiempos y determinación del ganador
        String mensajeGanador;
        int ganador = tiemposPorJugador.getOrDefault(0, Long.MAX_VALUE) < tiemposPorJugador.getOrDefault(1, Long.MAX_VALUE) ? 0 : 1;

        if (tiemposPorJugador.getOrDefault(0, Long.MAX_VALUE).equals(tiemposPorJugador.getOrDefault(1, Long.MAX_VALUE))) {
            mensajeGanador = "¡Empate!";
        } else {
            mensajeGanador = "¡Jugador " + (ganador + 1) + " es el ganador!";
        }

        // mostramos puntuación y tiempos por jugador
        JOptionPane.showMessageDialog(
                ventana,
                "Jugador 1 - Aciertos: " + puntuacion[0] + " - Tiempo: " + obtenerTiempoJugador(0) + "s\n" +
                        "Jugador 2 - Aciertos: " + puntuacion[1] + " - Tiempo: " + obtenerTiempoJugador(1) + "s\n" +
                        "Tiempo total: " + tiempoFinal / 1000.0 + "s\n\n" +
                        mensajeGanador,
                "Fin del Juego",
                JOptionPane.INFORMATION_MESSAGE);

        reiniciarJuego();
    }

    // obtener tiempo del jugador actual
    private double obtenerTiempoJugador(int jugador) {
        Long tiempo = tiemposPorJugador.get(jugador);
        return tiempo != null ? tiempo / 1000.0 : 0.0;
    }

    // comprobamos si la letra pulsada coincide
    public void comprobarLetra(char letra) {
        if (cronometro.isRunning()) {
            if (letra == letraActual) {
                puntuacion[jugadorActual]++;
            } else {
                // Si hay error, aumentamos el tiempo del jugador en 0.5 segundos
                tiemposPorJugador.put(jugadorActual, tiemposPorJugador.getOrDefault(jugadorActual, 0L) + 500);
            }

            // Verificamos si el jugador actual ha terminado todas sus rondas
            if (rondaActual >= CANTIDAD_RONDAS) {
                cambiarTurno();
            } else {
                generarLetraAleatoria();
            }
        }
    }

    // Método para cambiar al siguiente jugador o finalizar el juego si ambos jugadores han terminado
    private void cambiarTurno() {
        if (jugadorActual == 0) {
            jugadorActual = 1; // Pasamos al jugador 2
            rondaActual = 0; // Reiniciamos las rondas para el jugador 2
            boton.setText("Turno Jugador 2");
        } else {
            finalizarJuego(); // Ambos jugadores han terminado, finalizamos el juego
        }
        letra.setText(""); // Limpiamos la letra mostrada en pantalla
    }

    // genera una letra minúscula según el jugador actual
    public void generarLetraAleatoria() {
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

    private void mostrarVentanaAnterior() {
        JFrame ventanaAnterior = new Dificultad_();  
        ventanaAnterior.setVisible(true);
    }
    
    
    public static void main(String[] args) {
        // Cerrar la ventana anterior (si existe)
        for (Window window : Window.getWindows()) {
            if (window instanceof JFrame) {
                window.dispose();
            }
        }

        // Crear e iniciar la nueva instancia de JuegoMecanografiaDificil
        SwingUtilities.invokeLater(() -> new JuegoMecanografiaDificil());
    }
}