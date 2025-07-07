package clientes;

public class PessoaFisica extends Cliente {

    //construtor
    public PessoaFisica(String NomePessoa, String Nascimento, String CPF, String Email, String Senha, String Telefone,
            String Estado, String Cidade, String Bairro, String Rua, int Numero, String Complemento, String CEP) {
        super(NomePessoa, Nascimento, CPF, Email, Senha, Telefone, Estado, Cidade, Bairro, Rua, Numero, Complemento, CEP);
    }
    
    //getters possivelmente necessários
    public String getNomePessoa(){
        return NomePessoa;
    }

    public String getCPF(){
        return CPF;
    }

    public String getEmail(){
        return Email;
    }

    public String getSenha(){
        return Senha;
    }

}
