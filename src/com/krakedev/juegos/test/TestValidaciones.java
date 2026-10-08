package com.krakedev.juegos.test;

import java.util.ArrayList;
import java.util.HashSet;
import com.krakedev.juegos.entidades.Carta;
import com.krakedev.juegos.entidades.Jugador;
import com.krakedev.juegos.servicios.Dealer;
import com.krakedev.juegos.servicios.Juego21;

public class TestValidaciones {
    private static int verificaciones;
    private static void verificar(boolean condicion, String mensaje) {
        if (!condicion) { throw new AssertionError(mensaje); }
        verificaciones++;
    }
    private static Juego21 crearJuego() {
        Juego21 juego = new Juego21();
        juego.agregarJugador(new Jugador("Uno"));
        juego.agregarJugador(new Jugador("Dos"));
        juego.agregarJugador(new Jugador("Tres"));
        juego.inicializar();
        return juego;
    }
    private static void prepararCartas(Juego21 juego, int... valores) {
        Dealer dealer = new Dealer() {
            @Override public int generarAleatorio(int maximo) { return 0; }
        };
        ArrayList<Carta> cartas = new ArrayList<Carta>();
        for (int valor : valores) {
            Carta carta = new Carta(Integer.toString(valor), "T");
            carta.setValorJuego(valor);
            cartas.add(carta);
        }
        dealer.setNaipe(cartas);
        juego.setDealer(dealer);
    }
    public static void main(String[] args) {
        Dealer dealer = new Dealer();
        HashSet<String> cartas = new HashSet<String>();
        for (Carta carta : dealer.getNaipe()) {
            verificar(carta.getValorJuego() == 0, "Dealer no asigna puntos");
            cartas.add(carta.getValor() + carta.getPalo());
        }
        verificar(cartas.size() == 52, "Mazo completo sin duplicados");
        dealer.generarNaipe();
        verificar(dealer.getNaipe().size() == 52, "Regenerar no duplica mazo");
        HashSet<Carta> entregadas = new HashSet<Carta>();
        for (int i = 0; i < 52; i++) {
            Carta carta = dealer.entregarCarta();
            verificar(entregadas.add(carta), "No entregar dos veces la misma carta");
            verificar(!dealer.getNaipe().contains(carta), "Eliminar carta entregada");
        }
        verificar(dealer.getNaipe().isEmpty(), "Mazo agotado");
        verificar(dealer.generarAleatorio(0) == 0, "Rango de un solo valor");
        for (int i = 0; i < 1000; i++) {
            int numero = dealer.generarAleatorio(51);
            verificar(numero >= 0 && numero <= 51, "Limites del aleatorio");
        }
        Juego21 juego = crearJuego();
        for (Carta carta : juego.getDealer().getNaipe()) {
            String valor = carta.getValor();
            int esperado = valor.equals("A") ? 11 :
                (valor.equals("J") || valor.equals("Q") || valor.equals("K")) ? 10 : Integer.parseInt(valor);
            verificar(carta.getValorJuego() == esperado, "Valor del juego");
        }
        prepararCartas(juego, 11, 11, 10, 10, 10, 10, 2, 2, 2);
        ArrayList<Jugador> ganadores = juego.jugar();
        verificar(ganadores.size() == 2, "Dos ganadores simultaneos");
        verificar(ganadores.contains(juego.getJugadores().get(0)), "Ganador uno");
        verificar(ganadores.contains(juego.getJugadores().get(1)), "Ganador dos");
        verificar(!ganadores.contains(juego.getJugadores().get(2)), "20 no gana");
        for (Jugador jugador : juego.getJugadores()) {
            verificar(jugador.getCartas().size() == 2, "Break detiene antes de tercera ronda");
        }
        juego.calcularTotal();
        verificar(juego.getJugadores().get(0).getPuntajeCartas() == 21, "Recalcular no duplica total");
        juego.inicializar();
        for (Jugador jugador : juego.getJugadores()) {
            verificar(jugador.getCartas().isEmpty() && jugador.getPuntajeCartas() == 0, "Reinicio limpio");
        }
        verificar(juego.getDealer().getNaipe().size() == 52, "Reinicio del naipe");
        prepararCartas(juego, 10, 2, 3, 10, 2, 3, 10, 2, 3);
        verificar(juego.jugar().isEmpty(), "Sin 21 no hay ganadores; 30 tampoco gana");
        for (Jugador jugador : juego.getJugadores()) {
            verificar(jugador.getCartas().size() == 3, "Maximo tres rondas");
        }
        verificar(juego.getJugadores().get(0).getPuntajeCartas() == 30, "Suma sobre 21");
        System.out.println("Verificaciones correctas: " + verificaciones);
    }
}
