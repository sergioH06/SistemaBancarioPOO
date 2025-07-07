package clientes;

import utilidades.ValidadorCPF;

import java.util.Scanner;



public class CadastroPessoaFisica {
    public static PessoaFisica CadastrarPessoaFisica(Scanner scanner){

        System.out.println("===== CADASTRO DE PESSOA FÍSICA =====");
        // entrada dos dados pessoais
        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Data de nascimento (dd/mm/aaaa): ");
        String nascimento = scanner.nextLine();

        String CPF;
        boolean CPFvalido = false;

        do {
            System.out.print("CPF (apenas números): ");
            CPF = scanner.nextLine();
            CPFvalido = ValidadorCPF.validarCPF(CPF);
        } while (!CPFvalido);

        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();

        System.out.println("\n===== CADASTRO DE ENDEREÇO =====");

        System.out.print("Estado: ");
        String estado = scanner.nextLine();

        System.out.print("Cidade: ");
        String cidade = scanner.nextLine();

        System.out.print("Bairro: ");
        String bairro = scanner.nextLine();

        System.out.print("Rua: ");
        String rua = scanner.nextLine();

        System.out.print("Número: ");
        int numero = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Complemento: ");
        String complemento = scanner.nextLine();

        System.out.print("CEP: ");
        String cep = scanner.nextLine();

        System.out.println("\n===== CADASTRO DE LOGIN =====");
        
        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        PessoaFisica cliente = new PessoaFisica(nome, nascimento, CPF, email, senha, telefone, estado, cidade, bairro, rua, numero, complemento, cep);

        return cliente;
    }
}
