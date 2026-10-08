package com.krakedev.juegos.test;

import com.krakedev.juegos.servicios.Dealer;

public class TestAleatorio {
    public static void main(String[] args) {
        Dealer dealer = new Dealer();
        int maximo = 5;
        boolean salioCero = false;
        boolean salioMaximo = false;
        for (int i = 0; i < 100; i++) {
            int numero = dealer.generarAleatorio(maximo);
            System.out.println(numero);
            if (numero < 0 || numero > maximo) { throw new AssertionError("Fuera del rango"); }
            if (numero == 0) { salioCero = true; }
            if (numero == maximo) { salioMaximo = true; }
        }
        System.out.println("Salio cero: " + salioCero);
        System.out.println("Salio maximo: " + salioMaximo);
        // Una muestra aleatoria finita no garantiza que aparezcan ambos extremos.
    }
}
