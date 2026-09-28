import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;




public class OrdemServico {

    
    public static final DateTimeFormatter FORMATO_DATA =
            DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);

    private static int proximoCodigo = 1; 

    private final int codigo;
    private final String nomeCliente;
    private final String modeloVeiculo;
    private final String placaVeiculo;
    private final LocalDate data;
    private final double valorEstimado;
    private final Servico servico;
    private StatusOrdem status;
    private Box box; 

    public OrdemServico(String nomeCliente, String modeloVeiculo, String placaVeiculo,
                        LocalDate data, double valorEstimado, Servico servico) {
        this.codigo = proximoCodigo++;
        this.nomeCliente = nomeCliente;
        this.modeloVeiculo = modeloVeiculo;
        this.placaVeiculo = placaVeiculo;
        this.data = data;
        this.valorEstimado = valorEstimado;
        this.servico = servico;
        this.status = StatusOrdem.ABERTA; 
    }

    public boolean estaAberta() {
        return status == StatusOrdem.ABERTA;
    }

    
    
    void iniciarExecucao(Box box) {
        this.box = box;
        this.status = StatusOrdem.EM_EXECUCAO;
    }

    



    public void finalizar() {
        if (status != StatusOrdem.EM_EXECUCAO) {
            throw new OperacaoInvalidaException("Só é possível finalizar ordens em execução (a ordem #"
                    + codigo + " está \"" + status + "\").");
        }
        status = StatusOrdem.FINALIZADA;
        box.removerOrdem(this); 
    }

    
    public String resumo() {
        return String.format("#%d | %s | %s | %s (%s) | %s [%s] | %s",
                codigo, data.format(FORMATO_DATA), nomeCliente, modeloVeiculo, placaVeiculo,
                servico.getNome(), servico.getCategoria(), status);
    }

    public int getCodigo() {
        return codigo;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public String getModeloVeiculo() {
        return modeloVeiculo;
    }

    public String getPlacaVeiculo() {
        return placaVeiculo;
    }

    public LocalDate getData() {
        return data;
    }

    public double getValorEstimado() {
        return valorEstimado;
    }

    public Servico getServico() {
        return servico;
    }

    public StatusOrdem getStatus() {
        return status;
    }

    public Box getBox() {
        return box;
    }

    
    @Override
    public String toString() {
        String textoBox = "não atribuído";
        String textoMecanico = "nenhum";
        if (box != null) {
            textoBox = box.getNumero() + " - " + box.getLocalizacao();
            textoMecanico = box.getMecanicoResponsavel().toString();
        }
        return "Ordem " + codigo + "\n"
                + "Cliente: " + nomeCliente + "\n"
                + "Veículo: " + modeloVeiculo + " - Placa: " + placaVeiculo + "\n"
                + "Data: " + data.format(FORMATO_DATA) + "\n"
                + "Status: " + status + "\n"
                + "Valor estimado: R$ " + String.format("%.2f", valorEstimado) + "\n"
                + "Serviço: " + servico + "\n"
                + "Box: " + textoBox + "\n"
                + "Mecânico: " + textoMecanico;
    }
}
