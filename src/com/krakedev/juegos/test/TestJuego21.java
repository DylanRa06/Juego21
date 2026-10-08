package com.krakedev.juegos.test;

import java.util.ArrayList;
import com.krakedev.juegos.entidades.Carta;
import com.krakedev.juegos.entidades.Jugador;
import com.krakedev.juegos.servicios.Juego21;

public class TestJuego21 {
    public static void main(String[] args) {
        Juego21 juego = new Juego21();
        juego.agregarJugador(new Jugador("Dylan"));
        juego.agregarJugador(new Jugador("Juan"));
        juego.agregarJugador(new Jugador("Maria"));

        System.out.println("PRUEBA: UNA RONDA");
        juego.inicializar();
        juego.repartirRonda();
        for (Jugador jugador : juego.getJugadores()) {
            jugador.imprimir();
            for (Carta carta : jugador.getCartas()) {
                if (juego.getDealer().getNaipe().contains(carta)) {
                    throw new AssertionError("La carta repartida sigue en el naipe");
                }
            }
        }
        System.out.println("Cartas restantes: " + juego.getDealer().getNaipe().size());
        juego.getDealer().imprimirNaipe();

        System.out.println("PRIMERA PRUEBA: JUGAR");
        juego.inicializar();
        imprimirResultado(juego, juego.jugar());

        System.out.println("SEGUNDA PRUEBA: DIEZ PARTIDAS");
        int partidasConGanador = 0;
        for (int i = 0; i < 10; i++) {
            System.out.println("Partida " + (i + 1));
            juego.inicializar();
            ArrayList<Jugador> ganadores = juego.jugar();
            imprimirResultado(juego, ganadores);
            if (!ganadores.isEmpty()) { partidasConGanador++; }
        }
        System.out.println("Partidas con ganador: " + partidasConGanador);
    }
    private static void imprimirResultado(Juego21 juego, ArrayList<Jugador> ganadores) {
        for (Jugador jugador : juego.getJugadores()) { jugador.imprimir(); }
        if (ganadores.isEmpty()) { System.out.println("Sin ganadores"); }
        for (Jugador ganador : ganadores) {
            System.out.println("Ganador: " + ganador.getNickname());
        }
    }
}
