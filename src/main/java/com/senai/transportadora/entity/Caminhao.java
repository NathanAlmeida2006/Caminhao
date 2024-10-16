package com.senai.transportadora.entity;

public class Caminhao {
    private int id;
    private String placa;
    private String modelo;
    private String cor;
    private String fabricante;
    private String numeroChassis;
    private double capacidadeCarga;

    public Caminhao() {
    }

    public Caminhao(int id, String placa, String modelo, String cor, String fabricante, String numeroChassis, double capacidadeCarga) {
        this.id = id;
        this.placa = placa;
        this.modelo = modelo;
        this.cor = cor;
        this.fabricante = fabricante;
        this.numeroChassis = numeroChassis;
        this.capacidadeCarga = capacidadeCarga;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getNumeroChassis() {
        return numeroChassis;
    }

    public void setNumeroChassis(String numeroChassis) {
        this.numeroChassis = numeroChassis;
    }

    public double getCapacidadeCarga() {
        return capacidadeCarga;
    }

    public void setCapacidadeCarga(double capacidadeCarga) {
        this.capacidadeCarga = capacidadeCarga;
    }

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
