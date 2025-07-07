package app;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import clientes.CadastroPessoaFisica;
import clientes.CadastroPessoaJuridica;
import clientes.Cliente;
import clientes.PessoaFisica;
import clientes.PessoaJuridica;
import contas.Conta;
import contas.ContaCorrente;
import contas.ContaPoupanca;
import servicos.Transferir;

public class Main {

    private static List<Conta> contasCadastradas = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcao;
        do {
            exibirMenuPrincipal();
            opcao = escolherOpcao();

            switch (opcao) {
                case 1:
                    criarConta();
                    break;
                case 2:
                    entrarEmContaExistente();
                    break;
                case 0:
                    System.out.println("Saindo do sistema. Obrigado!");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
            System.out.println("\n");
        } while (opcao != 0);

        scanner.close();
    }

    public static void exibirMenuPrincipal() {
        System.out.println("\n===== MENU PRINCIPAL =====");
        System.out.println("[1] Criar Conta");
        System.out.println("[2] Entrar em Conta Existente");
        System.out.println("[0] Sair");
        System.out.print("Escolha uma opção: ");
    }

    public static void exibirTiposDeCliente() {
        System.out.println("\n===== TIPOS DE CLIENTE =====");
        System.out.println("[1] Pessoa Física");
        System.out.println("[2] Pessoa Jurídica");
        System.out.print("Escolha uma opção: ");
    }

    public static void exibirTiposDeConta() {
        System.out.println("\n===== TIPOS DE CONTA =====");
        System.out.println("[1] Conta Corrente");
        System.out.println("[2] Conta Poupança");
        System.out.print("Escolha uma opção: ");
    }

    public static void exibirMenuOperacoesConta(String numeroConta) {
        System.out.println("\n===== OPERAÇÕES DA CONTA " + numeroConta + " =====");
        System.out.println("1. Consultar Extrato");
        System.out.println("2. Depositar");
        System.out.println("3. Sacar");
        System.out.println("4. Transferir");
        System.out.println("0. Voltar ao Menu Principal");
        System.out.print("Escolha uma opção: ");
    }

    // --- Método Auxiliar para Escolha de Opção ---

    public static int escolherOpcao() {
        try {
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a nova linha
            return opcao;
        } catch (InputMismatchException exception) {
            System.out.println("Entrada inválida. Por favor, digite um número.\n");
            scanner.nextLine(); // Limpar o buffer do scanner para evitar loop infinito
            return -1; // Retorna um valor inválido para que o loop continue e o usuário tente novamente
        }
    }

    // --- Métodos de Funcionalidades Principais ---

    public static void criarConta() {
        exibirTiposDeCliente();
        int tipoDeCliente = escolherOpcao();

        Cliente novoCliente = null;
        switch (tipoDeCliente) {
            case 1:
                novoCliente = CadastroPessoaFisica.CadastrarPessoaFisica(scanner);
                break;
            case 2:
                novoCliente = CadastroPessoaJuridica.CadastrarPessoaJuridica(scanner);
                break;
            default:
                System.out.println("Opção inválida. Digite uma das opções listadas.");
                return;
        }

        if (novoCliente == null) {
            System.out.println("Erro ao cadastrar cliente. Retornando ao menu principal.");
            return;
        }

        exibirTiposDeConta();
        int tipoDeConta = escolherOpcao();
        System.out.print("Informe o saldo inicial: R$ ");
        double saldoInicial = scanner.nextDouble();
        scanner.nextLine();

        Conta novaConta = null;
        switch (tipoDeConta) {
            case 1:
                System.out.print("Informe o limite do cheque especial: R$ ");
                double limiteChequeEspecial = scanner.nextDouble();
                scanner.nextLine();
                if (novoCliente instanceof PessoaFisica) {
                    novaConta = new ContaCorrente((PessoaFisica) novoCliente, saldoInicial, limiteChequeEspecial, novoCliente.getEmail(), novoCliente.getSenha());
                } else if (novoCliente instanceof PessoaJuridica) {
                    novaConta = new ContaCorrente((PessoaJuridica) novoCliente, saldoInicial, limiteChequeEspecial, novoCliente.getEmail(), novoCliente.getSenha());
                }
                break;
            case 2:
                if (novoCliente instanceof PessoaFisica) {
                    novaConta = new ContaPoupanca((PessoaFisica) novoCliente, saldoInicial, novoCliente.getEmail(), novoCliente.getSenha());
                } else if (novoCliente instanceof PessoaJuridica) {
                    novaConta = new ContaPoupanca((PessoaJuridica) novoCliente, saldoInicial, novoCliente.getEmail(), novoCliente.getSenha());
                }
                break;
            default:
                System.out.println("Opção inválida de tipo de conta. Retornando ao menu principal.");
                return;
        }

        if (novaConta != null) {
            contasCadastradas.add(novaConta);
            System.out.println("\nConta criada com sucesso!");
            System.out.printf("Número da Conta: %s\n", novaConta.getNumeroDaConta());
            novaConta.gerarExtrato();
        } else {
            System.out.println("Não foi possível criar a conta. Verifique os dados e tente novamente.");
        }
    }

    private static void entrarEmContaExistente() {
        if (contasCadastradas.isEmpty()) {
            System.out.println("Nenhuma conta cadastrada ainda. Crie uma conta primeiro.");
            return;
        }

        System.out.println("\n===== ENTRAR EM CONTA =====");
        System.out.print("Email: ");
        String emailLogin = scanner.nextLine();
        System.out.print("Senha: ");
        String senhaLogin = scanner.nextLine();

        List<Conta> contasDoUsuario = new ArrayList<>();
        for (Conta conta : contasCadastradas) {
            if (conta.getEmail().equals(emailLogin) && conta.getSenha().equals(senhaLogin)) {
                contasDoUsuario.add(conta);
            }
        }

        if (contasDoUsuario.isEmpty()) {
            System.out.println("Email ou senha incorretos, ou nenhuma conta encontrada para este login.");
            return;
        }

        System.out.println("\nContas encontradas para o seu login:");
        for (int i = 0; i < contasDoUsuario.size(); i++) {
            Conta c = contasDoUsuario.get(i);
            String tipo = (c instanceof ContaCorrente) ? "Corrente" : "Poupança";
            System.out.printf("%d. Tipo: %s | Número: %s | Saldo: R$ %.2f\n", (i + 1), tipo, c.getNumeroDaConta(), c.getSaldo());
        }

        System.out.print("Escolha o número da conta que deseja acessar: ");
        int escolhaConta = escolherOpcao();

        if (escolhaConta > 0 && escolhaConta <= contasDoUsuario.size()) {
            Conta contaSelecionada = contasDoUsuario.get(escolhaConta - 1);
            menuOperacoesConta(contaSelecionada);
        } else {
            System.out.println("Seleção de conta inválida. Retornando ao menu principal.");
        }
    }

    private static void menuOperacoesConta(Conta conta) {
        int opcao;
        do {
            exibirMenuOperacoesConta(conta.getNumeroDaConta());
            opcao = escolherOpcao();

            switch (opcao) {
                case 1:
                    conta.gerarExtrato();
                    break;
                case 2:
                    System.out.print("Informe o valor a depositar: R$ ");
                    double valorDeposito = scanner.nextDouble();
                    scanner.nextLine();
                    conta.depositar(valorDeposito);
                    break;
                case 3:
                    System.out.print("Informe o valor a sacar: R$ ");
                    double valorSaque = scanner.nextDouble();
                    scanner.nextLine();
                    conta.sacar(valorSaque);
                    break;
                case 4:
                    System.out.print("Informe o número da conta de destino: ");
                    String numeroContaDestino = scanner.nextLine();
                    System.out.print("Informe o valor a transferir: R$ ");
                    double valorTransferencia = scanner.nextDouble();
                    scanner.nextLine();

                    Transferir.realizarTransferencia(conta, numeroContaDestino, valorTransferencia, contasCadastradas);
                    break;
                case 0:
                    System.out.println("Saindo das operações da conta.");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
            System.out.println("\n");
        } while (opcao != 0);
    }
}