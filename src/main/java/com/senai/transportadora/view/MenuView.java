package com.senai.transportadora.view;

import com.senai.transportadora.controller.CaminhaoController;
import com.senai.transportadora.controller.FuncionarioController;
import java.util.Scanner;

public class MenuView {
    private final CaminhaoView caminhaoView;
    private final FuncionarioView funcionarioView;
    private final Scanner scanner;

    public MenuView(CaminhaoController caminhaoController, FuncionarioController funcionarioController) {
        this.caminhaoView = new CaminhaoView(caminhaoController);
        this.funcionarioView = new FuncionarioView(funcionarioController);
        this.scanner = new Scanner(System.in);
    }

    public void exibirMenuPrincipal() {
        while (true) {
            System.out.println("\n=== SISTEMA DE TRANSPORTADORA ===");
            System.out.println("1. Gerenciar Funcionários");
            System.out.println("2. Gerenciar Caminhões");
            System.out.println("3. Sair do Sistema");
            System.out.print("Escolha uma opção: ");

            try {
                int opcao = Integer.parseInt(scanner.nextLine());

                switch (opcao) {
                    case 1:
                        funcionarioView.exibirMenu();
                        break;
                    case 2:
                        caminhaoView.exibirMenu();
                        break;
                    case 3:
                        System.out.println("Encerrando o sistema...");
                        System.exit(0);
                    default:
                        System.out.println("Opção inválida! Por favor, tente novamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida! Por favor, digite um número.");
            }
        }
    }
}