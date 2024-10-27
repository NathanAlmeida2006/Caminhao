package com.senai.transportadora.entity;

/**
 * Representa um caminhão utilizado pela transportadora.
 * <p>
 * Esta classe armazena informações sobre o caminhão, incluindo
 * sua placa, modelo, cor, fabricante, número do chassis e capacidade de carga.
 * </p>
 *
 * @param id              Identificador único do caminhão.
 * @param placa           Placa do caminhão.
 * @param modelo          Modelo do caminhão.
 * @param cor             Cor do caminhão.
 * @param fabricante      Fabricante do caminhão.
 * @param numeroChassis   Número do chassis do caminhão.
 * @param capacidadeCarga Capacidade de carga do caminhão em quilogramas.
 */
public record Caminhao(
        int id,
        String placa,
        String modelo,
        String cor,
        String fabricante,
        String numeroChassis,
        double capacidadeCarga
) {
}
