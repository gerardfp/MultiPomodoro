package com.company;

import java.util.Scanner;

class ContadorAtras {
    public interface OyenteDelProgreso {
        void cuandoPaseUnSegundo(String nombre, int segundosRestantes);
        void cuandoTermine(String nombre);
    }

    String nombre;
    int segundosRestantes;

    OyenteDelProgreso oyenteDelProgreso;

    public ContadorAtras(String nombre, int segundos) {
        this.nombre = nombre;
        this.segundosRestantes = segundos;
    }

    public void establecerOyenteDelProgreso(OyenteDelProgreso oyenteDelProgreso) {
        this.oyenteDelProgreso = oyenteDelProgreso;
    }

    public void iniciarAsincrono() {
        new Thread(this::iniciar).start();
    }

    public void iniciar() {
        long tiempoA = System.nanoTime();
        while(segundosRestantes > 0){
            long tiempoB = System.nanoTime();

            if(tiempoB-tiempoA > 1000000000){
                tiempoA = tiempoB;
                segundosRestantes--;
                oyenteDelProgreso.cuandoPaseUnSegundo(nombre, segundosRestantes);
            }
        }
        oyenteDelProgreso.cuandoTermine(nombre);
    }
}

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.println("Nombre:");
            String nombre = scanner.nextLine();
            System.out.println("Segundos:");
            int segundos = scanner.nextInt();
            scanner.nextLine();

            ContadorAtras contadorAtras = new ContadorAtras(nombre, segundos);
            contadorAtras.establecerOyenteDelProgreso(new ContadorAtras.OyenteDelProgreso(){

                @Override
                public void cuandoPaseUnSegundo(String nombre, int segundosRestantes) {
                    System.out.println(nombre + " " + segundosRestantes);
                }

                @Override
                public void cuandoTermine(String nombre) {
                    System.out.println("RIIIING! " + nombre);
                }
            });
            contadorAtras.iniciarAsincrono();
        }
    }
}
