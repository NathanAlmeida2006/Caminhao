package com.senai.transportadora.entity;

public record Usuario(
        int id,
        String nome,
        String email,
        String senha,
        String cargo
) {
}