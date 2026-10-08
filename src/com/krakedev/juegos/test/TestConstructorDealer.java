package com.krakedev.juegos.test;

import com.krakedev.juegos.servicios.Dealer;

public class TestConstructorDealer {
    public static void main(String[] args) {
        Dealer dealer = new Dealer();
        dealer.imprimirNaipe();
        System.out.println("Cantidad de cartas: " + dealer.getNaipe().size());
        if (dealer.getNaipe().size() != 52) { throw new AssertionError("Se esperan 52 cartas"); }
    }
}
