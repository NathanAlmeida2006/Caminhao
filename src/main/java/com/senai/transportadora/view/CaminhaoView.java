package com.senai.transportadora.view;

import com.senai.transportadora.controller.CaminhaoController;
import com.senai.transportadora.entity.Caminhao;

import java.util.Scanner;

public class CaminhaoView {

    private final CaminhaoController caminhaoController;
    private final Scanner scanner;

    public CaminhaoView(CaminhaoController caminhaoController) {
        this.caminhaoController = caminhaoController;
        this.scanner = new Scanner(System.in);
    }

    public void exibirMenu() {
        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Adicionar Caminhão");
            System.out.println("2. Listar Caminhões");
            System.out.println("3. Obter Caminhão por ID");
            System.out.println("4. Atualizar Caminhão");
            System.out.println("5. Remover Caminhão");
            System.out.println("6. Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = Integer.parseInt(scanner.nextLine());

            switch (opcao) {
                case 1:
                    adicionarCaminhao();
                    break;
                case 2:
                    listarCaminhoes();
                    break;
                case 3:
                    obterCaminhaoPorId();
                    break;
                case 4:
                    atualizarCaminhao();
                    break;
                case 5:
                    removerCaminhao();
                    break;
                case 6:
                    System.exit(0);
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        }
    }

    private void adicionarCaminhao() {
        System.out.print("Placa: ");
        String placa = scanner.nextLine();
        System.out.print("Modelo: ");
        String modelo = scanner.nextLine();
        System.out.print("Cor: ");
        String cor = scanner.nextLine();
        System.out.print("Fabricante: ");
        String fabricante = scanner.nextLine();
        System.out.print("Número do Chassis: ");
        String numeroChassis = scanner.nextLine();
        System.out.print("Capacidade de Carga: ");
        double capacidadeCarga = Double.parseDouble(scanner.nextLine());

        Caminhao caminhao = new Caminhao(0, placa, modelo, cor, fabricante, numeroChassis, capacidadeCarga);
        caminhaoController.adicionarCaminhao(caminhao);
        System.out.println("Caminhão adicionado com sucesso!");
    }

    private void listarCaminhoes() {
        System.out.println("\nLista de Caminhões:");
        for (Caminhao caminhao : caminhaoController.listarCaminhoes()) {
            System.out.println(caminhao);
        }
    }

    private void obterCaminhaoPorId() {
        System.out.print("ID do Caminhão: ");
        int id = Integer.parseInt(scanner.nextLine());
        Caminhao caminhao = caminhaoController.obterCaminhaoPorId(id);
        if (caminhao != null) {
            System.out.println(caminhao);
        } else {
            System.out.println("Caminhão não encontrado!");
        }
    }

    private void atualizarCaminhao() {
        System.out.print("ID do Caminhão a ser atualizado: ");
        int id = Integer.parseInt(scanner.nextLine());

        System.out.print("Nova Placa: ");
        String placa = scanner.nextLine();
        System.out.print("Novo Modelo: ");
        String modelo = scanner.nextLine();
        System.out.print("Nova Cor: ");
        String cor = scanner.nextLine();
        System.out.print("Novo Fabricante: ");
        String fabricante = scanner.nextLine();
        System.out.print("Novo Número do Chassis: ");
        String numeroChassis = scanner.nextLine();
        System.out.print("Nova Capacidade de Carga: ");
        double capacidadeCarga = Double.parseDouble(scanner.nextLine());

        Caminhao caminhaoAtualizado = new Caminhao(id, placa, modelo, cor, fabricante, numeroChassis, capacidadeCarga);
        boolean atualizado = caminhaoController.atualizarCaminhao(id, caminhaoAtualizado);
        if (atualizado) {
            System.out.println("Caminhão atualizado com sucesso!");
        } else {
            System.out.println("Caminhão não encontrado!");
        }
    }

    private void removerCaminhao() {
        System.out.print("ID do Caminhão a ser removido: ");
        int id = Integer.parseInt(scanner.nextLine());
        boolean removido = caminhaoController.removerCaminhao(id);
        if (removido) {
            System.out.println("Caminhão removido com sucesso!");
        } else {
            System.out.println("Caminhão não encontrado!");
        }
    }
}
