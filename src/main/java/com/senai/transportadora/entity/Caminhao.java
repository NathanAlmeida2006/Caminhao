package com.senai.transportadora.entity;

public record Caminhao(
        int id,
        String placa,
        String modelo,
        String cor,
        String fabricante,
        String numeroChassis,
        double capacidadeCarga
) {

    @Override
    public String toString() {
        return "-------------------------------------\n" +
                "ID: " + id + "\n" +
                "Placa: " + placa + "\n" +
                "Modelo: " + modelo + "\n" +
                "Cor: " + cor + "\n" +
                "Fabricante: " + fabricante + "\n" +
                "Número do Chassis: " + numeroChassis + "\n" +
                "Capacidade de Carga: " + capacidadeCarga + " kg\n" +
                "-------------------------------------";
    }
}
