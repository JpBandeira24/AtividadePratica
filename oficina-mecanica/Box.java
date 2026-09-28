import java.util.ArrayList;
import java.util.Collections;
import java.util.List;





public class Box {

    private final int numero;
    private final CategoriaServico tipoServicoPermitido;
    private final int capacidadeMaxima;
    private final String localizacao;
    private Mecanico mecanicoResponsavel;

    
    private final List<OrdemServico> ordens = new ArrayList<>();

    public Box(int numero, CategoriaServico tipoServicoPermitido, int capacidadeMaxima, String localizacao) {
        if (capacidadeMaxima <= 0) {
            throw new IllegalArgumentException("A capacidade máxima do box deve ser maior que zero.");
        }
        this.numero = numero;
        this.tipoServicoPermitido = tipoServicoPermitido;
        this.capacidadeMaxima = capacidadeMaxima;
        this.localizacao = localizacao;
    }

    



    public void definirMecanicoResponsavel(Mecanico mecanico) {
        if (mecanico.getBox() == this) {
            return; 
        }
        if (mecanico.possuiBox()) {
            throw new OperacaoInvalidaException(mecanico.getNome() + " já é responsável pelo box "
                    + mecanico.getBox().getNumero() + ", e um mecânico só pode cuidar de um box.");
        }
        if (mecanicoResponsavel != null) {
            mecanicoResponsavel.desvincularBox();
        }
        mecanicoResponsavel = mecanico;
        mecanico.vincularBox(this);
    }

    




    public void receberOrdem(OrdemServico ordem) {
        if (!ordem.estaAberta()) {
            throw new OperacaoInvalidaException("A ordem #" + ordem.getCodigo()
                    + " não está aberta (status atual: " + ordem.getStatus() + ").");
        }
        if (mecanicoResponsavel == null) {
            throw new OperacaoInvalidaException("O box " + numero + " ainda não tem mecânico responsável.");
        }
        CategoriaServico categoriaDaOrdem = ordem.getServico().getCategoria();
        if (categoriaDaOrdem != tipoServicoPermitido) {
            throw new OperacaoInvalidaException("O box " + numero + " só atende \"" + tipoServicoPermitido
                    + "\", e a ordem #" + ordem.getCodigo() + " é de \"" + categoriaDaOrdem + "\".");
        }
        if (estaLotado()) {
            throw new OperacaoInvalidaException("O box " + numero + " está lotado (máximo de "
                    + capacidadeMaxima + " veículo(s)).");
        }
        ordem.iniciarExecucao(this);
        ordens.add(ordem);
    }

    
    void removerOrdem(OrdemServico ordem) {
        ordens.remove(ordem);
    }

    public boolean estaLotado() {
        return ordens.size() >= capacidadeMaxima;
    }

    
    public List<OrdemServico> getOrdens() {
        return Collections.unmodifiableList(ordens);
    }

    public int getNumero() {
        return numero;
    }

    public CategoriaServico getTipoServicoPermitido() {
        return tipoServicoPermitido;
    }

    public int getCapacidadeMaxima() {
        return capacidadeMaxima;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public Mecanico getMecanicoResponsavel() {
        return mecanicoResponsavel;
    }

    @Override
    public String toString() {
        String responsavel = (mecanicoResponsavel == null) ? "sem responsável" : mecanicoResponsavel.getNome();
        return String.format("Box %d | Serviço permitido: %s | Ocupação: %d/%d | Local: %s | Mecânico: %s",
                numero, tipoServicoPermitido, ordens.size(), capacidadeMaxima, localizacao, responsavel);
    }
}
