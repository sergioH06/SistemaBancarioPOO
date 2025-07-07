package contas;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;

import java.util.Random;

public abstract class Conta {

    //Formatador de Data
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    
    //Getter Formatador
    public static DateTimeFormatter getFormatter() {
        return FORMATTER;
    }

    protected String numeroDaConta;
    protected  final String agencia = "1430";
    protected final String codigoBanco = "345";
    protected final String codigoOperacao = "001";
    private static final Random random = new Random();

    protected Object titular;
    protected double saldo;
    protected LocalDateTime dataCriacao;

    protected String email;
    protected String senha;

    public Conta(Object titular, double saldo, String email, String senha){
        this.titular = titular;
        this.saldo = saldo;
        this.dataCriacao = LocalDateTime.now();
        this.numeroDaConta = geradorConta();
        this.email = email;
        this.senha = senha;
    }

    //Getters Padrão
    public Object getTitular(){
        return titular;
    }

    public double getSaldo(){
        return saldo;
    }

    public LocalDateTime getDataCriacao(){
        return dataCriacao;
    }

    public String getNumeroDaConta(){
        return numeroDaConta;
    }

    public String getEmail(){
        return email;
    }

    public String getSenha(){
        return senha;
    }

    //Setters
    public void setSaldo(double saldo){
        this.saldo = saldo;
    }

    private String geradorConta(){
        StringBuilder contaGerar = new StringBuilder();
        for (int i = 0; i < 8; i++){
            contaGerar.append(random.nextInt(10));
        }
        String numeroContaGerar = contaGerar.toString();

        String digito = agencia + codigoOperacao + numeroContaGerar;

        int somaDigitos = 0;

        for (int i = 0; i < digito.length(); i++){
            somaDigitos += Character.getNumericValue(digito.charAt(i));
        }

        int digitoVerificador = somaDigitos % 10;

        return numeroContaGerar + "-" + digitoVerificador;
    }

    //Método de Depósito
    public void depositar(double valor){
        if(valor > 0){
            LocalDateTime agora = LocalDateTime.now();
            String dataHoraFormatada = agora.format(FORMATTER);
            this.saldo += valor;
            System.out.printf("Depósito de R$ %.2f realizado. Novo saldo: R$ %.2f.\n", valor, this.saldo);
            System.out.printf("Data e hora do depósito: %s.\n", dataHoraFormatada);
        } else {
            System.out.println("Valor de depósito inválido!\n");
        }
    }

    //Método de Saque
    public void sacar(double valor){
        if(valor > 0 && this.saldo >= valor){
            LocalDateTime agora = LocalDateTime.now();
            String dataHoraFormatada = agora.format(FORMATTER);
            this.saldo -= valor;
            System.out.printf("Saque de R$ %.2f realizado. Novo saldo: R$ %.2f.\n", valor, this.saldo);
            System.out.printf("Data e hora do saque: %s.\n", dataHoraFormatada);
        } else {
            System.out.println("Valor de saque inválido!\n");
        }
    }

    //Método Abstrato de Extrato
    public abstract void gerarExtrato();
}

