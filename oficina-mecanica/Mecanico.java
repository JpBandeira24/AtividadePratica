



public class Mecanico {

    private final String nome;
    private final String cpf;
    private final String especialidade;
    private final String telefone;
    private Box box; 

    public Mecanico(String nome, String cpf, String especialidade, String telefone) {
        this.nome = nome;
        this.cpf = cpf;
        this.especialidade = especialidade;
        this.telefone = telefone;
    }

    
    public boolean possuiCpf(String cpfInformado) {
        return apenasDigitos(cpf).equals(apenasDigitos(cpfInformado));
    }

    private static String apenasDigitos(String texto) {
        return texto.replaceAll("\\D", "");
    }

    public boolean possuiBox() {
        return box != null;
    }

    
    
    void vincularBox(Box box) {
        this.box = box;
    }

    void desvincularBox() {
        this.box = null;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public String getTelefone() {
        return telefone;
    }

    public Box getBox() {
        return box;
    }

    @Override
    public String toString() {
        return nome + " | CPF: " + cpf + " | Especialidade: " + especialidade + " | Tel.: " + telefone;
    }
}
