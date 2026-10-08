package com.krakedev.juegos.servicios;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import com.krakedev.juegos.entidades.Carta;

public class Dealer {
    private ArrayList<Carta> naipe;

    public Dealer() {
        naipe = new ArrayList<Carta>();
        generarNaipe();
    }
    public ArrayList<Carta> getNaipe() { return naipe; }
    public void setNaipe(ArrayList<Carta> naipe) { this.naipe = naipe; }
    public void generarNaipe() {
        naipe.clear();
        String[] palos = {"T", "CN", "CR", "D"};
        String[] valores = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        for (String palo : palos) {
            for (String valor : valores) {
                naipe.add(new Carta(valor, palo));
            }
        }
    }
    public void imprimirNaipe() {
        for (Carta carta : naipe) { carta.imprimir(); }
    }
    public int generarAleatorio(int maximo) {
        if (maximo < 0) { throw new IllegalArgumentException("El maximo no puede ser negativo"); }
        return (int) ThreadLocalRandom.current().nextLong((long) maximo + 1);
    }
    public Carta entregarCarta() {
        if (naipe.isEmpty()) { throw new IllegalStateException("No quedan cartas en el naipe"); }
        int posicion = generarAleatorio(naipe.size() - 1);
        Carta carta = naipe.get(posicion);
        naipe.remove(posicion);
        return carta;
    }
}
