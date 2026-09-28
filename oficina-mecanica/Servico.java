



public class Servico {

    private final String nome;
    private final double tempoEstimadoHoras;
    private final double valor;
    private final CategoriaServico categoria;

    public Servico(String nome, double tempoEstimadoHoras, double valor, CategoriaServico categoria) {
        this.nome = nome;
        this.tempoEstimadoHoras = tempoEstimadoHoras;
        this.valor = valor;
        this.categoria = categoria;
    }

    public String getNome() {
        return nome;
    }

    public double getTempoEstimadoHoras() {
        return tempoEstimadoHoras;
    }

    public double getValor() {
        return valor;
    }

    public CategoriaServico getCategoria() {
        return categoria;
    }

    @Override
    public String toString() {
        return String.format("%s | Categoria: %s | Tempo estimado: %.1f h | Valor: R$ %.2f",
                nome, categoria, tempoEstimadoHoras, valor);
    }
}
