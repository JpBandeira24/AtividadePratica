import java.util.ArrayList;
import java.util.Collections;
import java.util.List;






public class Oficina {

    private final List<Mecanico> mecanicos = new ArrayList<>();
    private final List<Box> boxes = new ArrayList<>();
    private final List<OrdemServico> ordens = new ArrayList<>();

    public void adicionarMecanico(Mecanico novo) {
        for (Mecanico mecanico : mecanicos) {
            if (mecanico.possuiCpf(novo.getCpf())) {
                throw new OperacaoInvalidaException("Já existe um mecânico com o CPF " + novo.getCpf() + ".");
            }
        }
        mecanicos.add(novo);
    }

    public void adicionarBox(Box novo) {
        for (Box box : boxes) {
            if (box.getNumero() == novo.getNumero()) {
                throw new OperacaoInvalidaException("Já existe um box com o número " + novo.getNumero() + ".");
            }
        }
        boxes.add(novo);
    }

    public void cadastrarOrdem(OrdemServico ordem) {
        ordens.add(ordem);
    }

    public Mecanico buscarMecanicoPorCpf(String cpf) {
        for (Mecanico mecanico : mecanicos) {
            if (mecanico.possuiCpf(cpf)) {
                return mecanico;
            }
        }
        throw new OperacaoInvalidaException("Nenhum mecânico encontrado com o CPF " + cpf + ".");
    }

    public Box buscarBoxPorNumero(int numero) {
        for (Box box : boxes) {
            if (box.getNumero() == numero) {
                return box;
            }
        }
        throw new OperacaoInvalidaException("Não existe box com o número " + numero + ".");
    }

    public OrdemServico buscarOrdemPorCodigo(int codigo) {
        for (OrdemServico ordem : ordens) {
            if (ordem.getCodigo() == codigo) {
                return ordem;
            }
        }
        throw new OperacaoInvalidaException("Não existe ordem com o código " + codigo + ".");
    }

    public List<OrdemServico> buscarOrdensPorStatus(StatusOrdem status) {
        List<OrdemServico> encontradas = new ArrayList<>();
        for (OrdemServico ordem : ordens) {
            if (ordem.getStatus() == status) {
                encontradas.add(ordem);
            }
        }
        return encontradas;
    }

    



    public int contarOrdensFinalizadas(Box box) {
        int total = 0;
        for (OrdemServico ordem : ordens) {
            if (ordem.getStatus() == StatusOrdem.FINALIZADA && ordem.getBox() == box) {
                total++;
            }
        }
        return total;
    }

    public List<Mecanico> getMecanicos() {
        return Collections.unmodifiableList(mecanicos);
    }

    public List<Box> getBoxes() {
        return Collections.unmodifiableList(boxes);
    }

    public List<OrdemServico> getOrdens() {
        return Collections.unmodifiableList(ordens);
    }
}
