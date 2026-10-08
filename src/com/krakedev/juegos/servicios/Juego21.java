package com.krakedev.juegos.servicios;

import java.util.ArrayList;
import com.krakedev.juegos.entidades.Carta;
import com.krakedev.juegos.entidades.Jugador;

public class Juego21 {
    private ArrayList<Jugador> jugadores = new ArrayList<Jugador>();
    private Dealer dealer;

    public ArrayList<Jugador> getJugadores() { return jugadores; }
    public void setJugadores(ArrayList<Jugador> jugadores) { this.jugadores = jugadores; }
    public Dealer getDealer() { return dealer; }
    public void setDealer(Dealer dealer) { this.dealer = dealer; }
    public void cargarValores() {
        for (Carta carta : dealer.getNaipe()) {
            String valor = carta.getValor();
            if (valor.equals("A")) { carta.setValorJuego(11); }
            else if (valor.equals("J") || valor.equals("Q") || valor.equals("K")) {
                carta.setValorJuego(10);
            } else { carta.setValorJuego(Integer.parseInt(valor)); }
        }
    }
    public void inicializar() {
        dealer = new Dealer();
        cargarValores();
        for (Jugador jugador : jugadores) {
            jugador.getCartas().clear();
            jugador.setPuntajeCartas(0);
        }
    }
    public void agregarJugador(Jugador jugador) { jugadores.add(jugador); }
    public void repartirCarta(Jugador jugador) {
        Carta carta = dealer.entregarCarta();
        jugador.recibirCarta(carta);
    }
    public void repartirRonda() {
        if (dealer == null) { throw new IllegalStateException("Primero invoque inicializar()"); }
        if (dealer.getNaipe().size() < jugadores.size()) {
            throw new IllegalStateException("No hay suficientes cartas para una ronda");
        }
        for (Jugador jugador : jugadores) { repartirCarta(jugador); }
        calcularTotal();
    }
    public void calcularTotal() {
        for (Jugador jugador : jugadores) {
            int total = 0;
            for (Carta carta : jugador.getCartas()) { total += carta.getValorJuego(); }
            jugador.setPuntajeCartas(total);
        }
    }
    public ArrayList<Jugador> validarGanador() {
        ArrayList<Jugador> ganadores = new ArrayList<Jugador>();
        for (Jugador jugador : jugadores) {
            if (jugador.getPuntajeCartas() == 21) { ganadores.add(jugador); }
        }
        return ganadores;
    }
    public ArrayList<Jugador> jugar() {
        ArrayList<Jugador> ganadores = new ArrayList<Jugador>();
        for (int ronda = 0; ronda < 3; ronda++) {
            repartirRonda();
            ganadores = validarGanador();
            if (!ganadores.isEmpty()) { break; }
        }
        return ganadores;
    }
}
