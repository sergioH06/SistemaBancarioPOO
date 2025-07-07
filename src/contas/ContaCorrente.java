package contas;

import clientes.PessoaJuridica;
import clientes.PessoaFisica;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;



public class ContaCorrente extends Conta {

    //Atributos
    private final double TAXA_MANUTENCAO_MENSAL = 10.00;
    private final double TAXA_POR_TRANSACAO_EXTRA = 2.50;
    private final int TRANSACOES_GRATUITAS_MES = 20;

    private double limiteChequeEspecial;
    private int contadorTransacoesMes = 0; 
    private int ultimoMesCobrancaTaxaManutencao;
    private int ultimoAnoCobrancaTaxaManutencao;
    private int diaAniversarioCobranca;

    //Construtor para Pessoa Física
    public ContaCorrente(PessoaFisica titular, double saldoInicial, double limiteChequeEspecial, String email, String senha){
        super(titular, saldoInicial, email, senha);
        this.limiteChequeEspecial = limiteChequeEspecial;
        this.diaAniversarioCobranca = this.dataCriacao.getDayOfMonth();
        this.ultimoMesCobrancaTaxaManutencao = this.dataCriacao.getMonthValue();
        this.ultimoAnoCobrancaTaxaManutencao = this.dataCriacao.getYear();
        System.out.println("Tipo de Conta: Corrente\n");
        System.out.printf("Limite de Cheque Especial: R$ %.2f\n", this.limiteChequeEspecial);
    }

    //Construtor para Pessoa Jurídica
    public ContaCorrente(PessoaJuridica titular, double saldoInicial, double limiteChequeEspecial, String email, String senha){
        super(titular, saldoInicial, email, senha);
        this.limiteChequeEspecial = limiteChequeEspecial;
        this.diaAniversarioCobranca = this.dataCriacao.getDayOfMonth();
        this.ultimoMesCobrancaTaxaManutencao = this.dataCriacao.getMonthValue();
        this.ultimoAnoCobrancaTaxaManutencao = this.dataCriacao.getYear();
        System.out.println("Tipo de Conta: Corrente\n");
        System.out.printf("Limite de Cheque Especial: R$ %.2f\n", this.limiteChequeEspecial);
    }

    //Getters
    public double getLimiteChequeEspecial() {
        return limiteChequeEspecial;
    }

    //Setters
    public void setLimiteChequeEspecial(double limiteChequeEspecial) {
        this.limiteChequeEspecial = limiteChequeEspecial;
    }

    //Método de Cobrança de Taxa de Manutenção Mensal
    public void cobrarTaxaManutencaoMensal(){
        LocalDate dataAtualSimulada = LocalDate.now();
        int mesAtualSimulado = dataAtualSimulada.getMonthValue();
        int anoAtualSimulado = dataAtualSimulada.getYear();
        LocalDate ultimoDiaDoMes = dataAtualSimulada.with(TemporalAdjusters.lastDayOfMonth());
        LocalDate dataCobrancaEsteMes;
        if(this.ultimoAnoCobrancaTaxaManutencao < anoAtualSimulado || (anoAtualSimulado == this.ultimoAnoCobrancaTaxaManutencao && this.ultimoMesCobrancaTaxaManutencao < mesAtualSimulado)){
            //Cria a data de cobrança deste mês
            if(this.dataCriacao.getDayOfMonth() > ultimoDiaDoMes.getDayOfMonth()){
                dataCobrancaEsteMes = dataAtualSimulada.with(TemporalAdjusters.lastDayOfMonth());
            } else {
                dataCobrancaEsteMes = LocalDate.of(anoAtualSimulado, mesAtualSimulado, diaAniversarioCobranca);
            }
            //Registra a última data de cobrança
            LocalDate ultimaDataDeCobranca = LocalDate.of(this.ultimoAnoCobrancaTaxaManutencao, this.ultimoMesCobrancaTaxaManutencao, this.diaAniversarioCobranca);
            if (this.diaAniversarioCobranca > ultimaDataDeCobranca.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth()) {
                ultimaDataDeCobranca = ultimaDataDeCobranca.with(TemporalAdjusters.lastDayOfMonth());
            }
            //Calcula a diferença de meses entre a data da última aplicação e a data de aplicação para esse mês
            long quantidadeDeMeses = ChronoUnit.MONTHS.between(ultimaDataDeCobranca, dataCobrancaEsteMes);

            if(quantidadeDeMeses == 1){
                if(dataAtualSimulada.isAfter(dataCobrancaEsteMes) || dataAtualSimulada.isEqual(dataCobrancaEsteMes)){
                    System.out.println("===== COBRANÇA DA TAXA DE MANUTENÇÃO MENSAL =====");
                    double saldoAntesDoRendimento = this.saldo;
                    this.saldo -= TAXA_MANUTENCAO_MENSAL;
                    System.out.printf("Saldo Anterior: R$ %.2f\n", saldoAntesDoRendimento);
                    System.out.printf("Taxa de Manutenção Mensal: R$ %.2f\n", TAXA_MANUTENCAO_MENSAL);
                    System.out.printf("Novo Saldo: R$ %.2f", this.saldo);
                    this.ultimoMesCobrancaTaxaManutencao = mesAtualSimulado;
                    this.ultimoAnoCobrancaTaxaManutencao = anoAtualSimulado;
                    this.contadorTransacoesMes = 0;
                } 
            } else if (quantidadeDeMeses >= 2){
                double saldoAntesDoRendimento = this.saldo;
                this.saldo -= TAXA_MANUTENCAO_MENSAL * quantidadeDeMeses - 1;
                if(dataAtualSimulada.isAfter(dataCobrancaEsteMes) || dataAtualSimulada.isEqual(dataCobrancaEsteMes)){
                    System.out.println("===== COBRANÇA DA TAXA DE MANUTENÇÃO MENSAL =====");
                    this.saldo -= TAXA_MANUTENCAO_MENSAL;
                    this.ultimoMesCobrancaTaxaManutencao = mesAtualSimulado;
                    this.ultimoAnoCobrancaTaxaManutencao = anoAtualSimulado;
                }
                System.out.printf("Saldo Anterior: R$ %.2f\n", saldoAntesDoRendimento);
                System.out.printf("Taxa de Manutenção Mensal: R$ %.2f\n", TAXA_MANUTENCAO_MENSAL);
                System.out.printf("Novo Saldo: R$ %.2f", this.saldo);
                this.contadorTransacoesMes = 0;
            }
        }
    }

    //Sobrescrição do Método de Saque
    @Override
    public void sacar(double valor){
        cobrarTaxaManutencaoMensal();
        if(valor <= 0){
            System.out.println("Valor de saque inválido!\n");
        } else {
            if(TRANSACOES_GRATUITAS_MES <= contadorTransacoesMes){
                this.saldo -= TAXA_POR_TRANSACAO_EXTRA;
            }
            if((this.saldo + this.limiteChequeEspecial >= valor)){
                if(this.saldo >= valor){
                    this.saldo -= valor;
                    System.out.printf("Saldo de R$ %.2f realizado na conta %s. Novo saldo: R$ %.2f\n", valor, getNumeroDaConta(), this.saldo);
                    this.contadorTransacoesMes += 1;
                } else {
                    double valorUsadoDoLimite = valor - this.saldo;
                    this.saldo = 0;
                    this.limiteChequeEspecial -= valorUsadoDoLimite;
                    System.out.printf("Saque de R$ %.2f realizado (parte do limite de cheque especial). Saldo atual: R$ %.2f. Limite restante: R$ %.2f.\n", valor, this.saldo, this.limiteChequeEspecial);
                    this.contadorTransacoesMes += 1;
                }
                if(TRANSACOES_GRATUITAS_MES <= contadorTransacoesMes){
                    System.out.println("Você possui 0 transações gratuítas restantes.\n");
                } else {
                    System.out.printf("Você possui %d transações gratuítas restantes.\n", TRANSACOES_GRATUITAS_MES - contadorTransacoesMes);
                }
            } else {
                System.out.println("Saldo e limite do cheque especial insuficientes. Não é possível realizar o saque.\n");
            }
        }    
    }

    @Override
    public void depositar(double valor){
        super.depositar(valor);
        cobrarTaxaManutencaoMensal();
    }
    
    //Definição do Método de Geração de Extrato
    @Override
    public void gerarExtrato(){
        System.out.println("\n===== EXTRATO CONTA CORRENTE ===== \n");
        System.out.printf("Número da Conta: %s\n", this.numeroDaConta);
        System.out.printf("Agência: %s\n", this.agencia);
        System.out.printf("Banco: %s\n", this.codigoBanco);
        System.out.printf("Operação: %s\n", this.codigoOperacao);

        if(this.titular instanceof PessoaFisica){
            System.out.printf("Titular: %s (CPF %s)\n", ((PessoaFisica) this.titular).getNomePessoa(), ((PessoaFisica) this.titular).getCPF());
        }
        if(this.titular instanceof PessoaJuridica){
            System.out.printf("Razão Social: %s (CNPJ %s)\n", ((PessoaJuridica) this.titular).getRazaoSocial(), ((PessoaJuridica) this.titular).getCNPJ());
            System.out.printf("Responsável: %s\n", ((PessoaJuridica) this.titular).getNomePessoa());
        }

        System.out.printf("Saldo: R$ %.2f\n", this.saldo);
        System.out.printf("Limite de Cheque Especial: R$ %.2f\n", this.limiteChequeEspecial);
        System.out.println("Data de Criação: " + this.dataCriacao.format(getFormatter()));
        System.out.printf("Número de Transações Efetuadas: %d\n", this.contadorTransacoesMes);
        if(TRANSACOES_GRATUITAS_MES > this.contadorTransacoesMes){
            System.out.printf("Número de Transações Gratuítas restantes: %d\n", TRANSACOES_GRATUITAS_MES - this.contadorTransacoesMes);
        } else {
            System.out.println("Número de Transações Gratuítas restantes: 0\n");
        }
    }
}