package com.senai.transportadora.view;

import com.senai.transportadora.controller.FuncionarioController;
import com.senai.transportadora.entity.Funcionario;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class FuncionarioView {
    private final FuncionarioController funcionarioController;
    private final Scanner scanner;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("ddMMyyyy");

    public FuncionarioView(FuncionarioController funcionarioController) {
        this.funcionarioController = funcionarioController;
        this.scanner = new Scanner(System.in);
    }

    private Funcionario lerDadosFuncionario() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("CPF: ");
        String cpf = scanner.nextLine();

        System.out.print("RG: ");
        String rg = scanner.nextLine();

        System.out.print("Data de Nascimento (ddMMaaaa): ");
        LocalDate dataNascimento = lerData();

        System.out.print("Cargo: ");
        String cargo = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();

        LocalDateTime dataCadastro = LocalDateTime.now();

        return new Funcionario(0, nome, cpf, rg, dataNascimento, cargo, email, telefone, dataCadastro);
    }

    public void exibirMenu() {
        while (true) {
            System.out.println("\n=== MENU FUNCIONÁRIOS ===");
            System.out.println("1. Adicionar Funcionário");
            System.out.println("2. Listar Funcionários");
            System.out.println("3. Buscar Funcionário");
            System.out.println("4. Atualizar Funcionário");
            System.out.println("5. Remover Funcionário");
            System.out.println("6. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            try {
                int opcao = Integer.parseInt(scanner.nextLine());

                switch (opcao) {
                    case 1:
                        adicionarFuncionario();
                        break;
                    case 2:
                        listarFuncionarios();
                        break;
                    case 3:
                        buscarFuncionario();
                        break;
                    case 4:
                        atualizarFuncionario();
                        break;
                    case 5:
                        removerFuncionario();
                        break;
                    case 6:
                        return;
                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida! Digite um número.");
            }
        }
    }

    private void adicionarFuncionario() {
        System.out.println("\n=== ADICIONAR FUNCIONÁRIO ===");
        Funcionario funcionario = lerDadosFuncionario();
        funcionarioController.adicionarFuncionario(funcionario);
        System.out.println("Funcionário adicionado com sucesso!");
    }

    private void listarFuncionarios() {
        System.out.println("\n=== LISTA DE FUNCIONÁRIOS ===");
        for (Funcionario funcionario : funcionarioController.listarFuncionarios()) {
            System.out.println(funcionario.toString());
        }
    }

    private void buscarFuncionario() {
        System.out.println("\n=== BUSCAR FUNCIONÁRIO ===");
        System.out.print("Digite o ID do funcionário: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            Funcionario funcionario = funcionarioController.obterFuncionarioPorId(id);
            System.out.println(funcionario.toString());
        } catch (NumberFormatException e) {
            System.out.println("ID inválido!");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void atualizarFuncionario() {
        System.out.println("\n=== ATUALIZAR FUNCIONÁRIO ===");
        System.out.print("Digite o ID do funcionário: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            Funcionario funcionarioAtualizado = lerDadosFuncionario();
            if (funcionarioController.atualizarFuncionario(id, funcionarioAtualizado)) {
                System.out.println("Funcionário atualizado com sucesso!");
            } else {
                System.out.println("Funcionário não encontrado!");
            }
        } catch (NumberFormatException e) {
            System.out.println("ID inválido!");
        }
    }

    private void removerFuncionario() {
        System.out.println("\n=== REMOVER FUNCIONÁRIO ===");
        System.out.print("Digite o ID do funcionário: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            if (funcionarioController.removerFuncionario(id)) {
                System.out.println("Funcionário removido com sucesso!");
            } else {
                System.out.println("Funcionário não encontrado!");
            }
        } catch (NumberFormatException e) {
            System.out.println("ID inválido!");
        }
    }

    private LocalDate lerData() {
        try {
            String dataStr = scanner.nextLine().trim();
            return LocalDate.parse(dataStr, dateFormatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Data inválida! Use o formato ddMMaaaa (exemplo: 09052006)");
        }
    }
}