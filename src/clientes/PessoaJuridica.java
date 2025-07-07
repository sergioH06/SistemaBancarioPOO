package clientes;

public class PessoaJuridica extends Cliente{

    protected String CNPJ;
    protected String RazaoSocial;

    //Construtor de Pessoa Jurídica
    public PessoaJuridica(String NomePessoa, String Nascimento, String CPF, String Email, String Senha, String Telefone,
            String Estado, String Cidade, String Bairro, String Rua, int Numero, String Complemento, String CEP, String CNPJ, String RazaoSocial) {
        super(NomePessoa, Nascimento, CPF, Email, Senha, Telefone, Estado, Cidade, Bairro, Rua, Numero, Complemento, CEP);
        this.CNPJ = CNPJ;
        this.RazaoSocial = RazaoSocial;
    }

    //Getters
    public String getNomePessoa(){
        return NomePessoa;
    }

    public String getRazaoSocial(){
        return RazaoSocial;
    }

    public String getCPF(){
        return CPF;
    }

    public String getCNPJ(){
        return CNPJ;
    }

    public String getEmail(){
        return Email;
    }

    public String getSenha(){
        return Senha;
    }

}