package servicos;

import java.util.List;

import contas.Conta;
import contas.ContaCorrente;

public class Transferir {

    public static void realizarTransferencia(Conta contaOrigem, String numeroContaDestino, double valorTransferencia, List<Conta> contasCadastradas) {
        if (valorTransferencia <= 0) {
            System.out.println("Valor de transferência inválido.");
            return;
        }

        Conta contaDestino = null;
        for (Conta c : contasCadastradas) {
            if (c.getNumeroDaConta().equals(numeroContaDestino)) {
                contaDestino = c;
                break;
            }
        }

        if (contaDestino == null) {
            System.out.println("Conta de destino não encontrada.");
            return;
        }

        if (contaOrigem.equals(contaDestino)) {
            System.out.println("Não é possível transferir para a mesma conta de origem.");
            return;
        }

        boolean podeSacar = false;
        if (contaOrigem instanceof ContaCorrente) {
            ContaCorrente ccOrigem = (ContaCorrente) contaOrigem;
            if (ccOrigem.getSaldo() + ccOrigem.getLimiteChequeEspecial() >= valorTransferencia) {
                podeSacar = true;
            }
        } else {
            if (contaOrigem.getSaldo() >= valorTransferencia) {
                podeSacar = true;
            }
        }

        if (podeSacar) {
            contaOrigem.sacar(valorTransferencia);
            contaDestino.depositar(valorTransferencia);
            System.out.printf("\nTransferência de R$ %.2f de %s para %s realizada com sucesso!\n",
                              valorTransferencia, contaOrigem.getNumeroDaConta(), contaDestino.getNumeroDaConta());
        } else {
            System.out.println("Saldo insuficiente na conta de origem para realizar a transferência.");
        }
    }
}
