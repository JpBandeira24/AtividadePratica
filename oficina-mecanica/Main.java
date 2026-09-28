import java.time.LocalDate;
import java.util.List;






public class Main {

    private static final Oficina oficina = new Oficina();
    private static final Entrada entrada = new Entrada();

    public static void main(String[] args) {
        criarDadosIniciais();

        int opcao;
        do {
            exibirMenu();
            opcao = entrada.lerInteiro("Escolha uma opção: ");
            try {
                executarOpcao(opcao);
            } catch (OperacaoInvalidaException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        } while (opcao != 0);

        entrada.fechar();
        System.out.println("Programa encerrado.");
    }

    
    private static void criarDadosIniciais() {
        oficina.adicionarMecanico(new Mecanico("Carlos Souza", "111.111.111-11", "Motor e câmbio", "(31) 99111-1111"));
        oficina.adicionarMecanico(new Mecanico("Ana Ribeiro", "222.222.222-22", "Elétrica automotiva", "(31) 99222-2222"));
        oficina.adicionarMecanico(new Mecanico("Paulo Mendes", "333.333.333-33", "Funilaria e pintura", "(31) 99333-3333"));

        oficina.adicionarBox(new Box(1, CategoriaServico.MECANICA, 2, "Galpão A - lado esquerdo"));
        oficina.adicionarBox(new Box(2, CategoriaServico.ELETRICA, 1, "Galpão A - lado direito"));
        oficina.adicionarBox(new Box(3, CategoriaServico.FUNILARIA, 3, "Galpão B"));
    }

    private static void exibirMenu() {
        System.out.println();
        System.out.println("MENU");
        System.out.println("1 - Cadastrar ordem de serviço");
        System.out.println("2 - Associar mecânico a um box");
        System.out.println("3 - Atribuir ordem de serviço a um box");
        System.out.println("4 - Exibir ordens de um box");
        System.out.println("5 - Total de ordens finalizadas por box");
        System.out.println("6 - Buscar ordens por status");
        System.out.println("7 - Exibir detalhes de uma ordem");
        System.out.println("8 - Finalizar ordem de serviço");
        System.out.println("0 - Sair");
    }

    private static void executarOpcao(int opcao) {
        switch (opcao) {
            case 1:
                cadastrarOrdem();
                break;
            case 2:
                associarMecanicoAoBox();
                break;
            case 3:
                atribuirOrdemAoBox();
                break;
            case 4:
                exibirOrdensDoBox();
                break;
            case 5:
                exibirFinalizadasPorBox();
                break;
            case 6:
                buscarOrdensPorStatus();
                break;
            case 7:
                exibirDetalhesDaOrdem();
                break;
            case 8:
                finalizarOrdem();
                break;
            case 0:
                break;
            default:
                System.out.println("Opção inválida.");
        }
    }

    
    private static void cadastrarOrdem() {
        String cliente = entrada.lerTexto("Nome do cliente: ");
        String modelo = entrada.lerTexto("Modelo do veículo: ");
        String placa = entrada.lerPlaca("Placa do veículo: ");
        LocalDate data = entrada.lerData("Data (dd/mm/aaaa) ou Enter para hoje: ");

        String nomeServico = entrada.lerTexto("Nome do serviço: ");
        double tempo = entrada.lerNumeroPositivo("Tempo estimado (horas): ");
        double valorServico = entrada.lerNumeroPositivo("Valor do serviço: ");
        CategoriaServico categoria = entrada.escolher("Escolha a categoria: ", CategoriaServico.values());
        Servico servico = new Servico(nomeServico, tempo, valorServico, categoria);

        double valorEstimado = entrada.lerNumeroPositivo(
                "Valor estimado da ordem (Enter para usar o valor do serviço): ", valorServico);

        OrdemServico ordem = new OrdemServico(cliente, modelo, placa, data, valorEstimado, servico);
        oficina.cadastrarOrdem(ordem);
        System.out.println("Ordem cadastrada com sucesso! Código: " + ordem.getCodigo());
    }

    
    private static void associarMecanicoAoBox() {
        listarMecanicos();
        Mecanico mecanico = oficina.buscarMecanicoPorCpf(entrada.lerTexto("CPF do mecânico: "));
        listarBoxes();
        Box box = oficina.buscarBoxPorNumero(entrada.lerInteiro("Número do box: "));

        box.definirMecanicoResponsavel(mecanico);
        System.out.println("Mecânico associado ao box com sucesso!");
    }

    
    private static void atribuirOrdemAoBox() {
        List<OrdemServico> abertas = oficina.buscarOrdensPorStatus(StatusOrdem.ABERTA);
        if (abertas.isEmpty()) {
            System.out.println("Não há ordens abertas.");
            return;
        }
        System.out.println("Ordens abertas:");
        listarResumos(abertas);
        OrdemServico ordem = oficina.buscarOrdemPorCodigo(entrada.lerInteiro("Código da ordem: "));
        listarBoxes();
        Box box = oficina.buscarBoxPorNumero(entrada.lerInteiro("Número do box: "));

        box.receberOrdem(ordem);
        System.out.println("Ordem atribuída ao box com sucesso!");
    }

    
    private static void exibirOrdensDoBox() {
        listarBoxes();
        Box box = oficina.buscarBoxPorNumero(entrada.lerInteiro("Número do box: "));
        List<OrdemServico> ordensDoBox = box.getOrdens();

        System.out.println("Ordens do box " + box.getNumero() + ":");
        listarResumos(ordensDoBox);
        System.out.println("Total de ordens: " + ordensDoBox.size());
    }

    
    private static void exibirFinalizadasPorBox() {
        for (Box box : oficina.getBoxes()) {
            System.out.println("Box " + box.getNumero() + ": " + oficina.contarOrdensFinalizadas(box)
                    + " ordem(ns) finalizada(s)");
        }
    }

    
    private static void buscarOrdensPorStatus() {
        StatusOrdem status = entrada.escolher("Escolha o status: ", StatusOrdem.values());
        List<OrdemServico> encontradas = oficina.buscarOrdensPorStatus(status);

        if (encontradas.isEmpty()) {
            System.out.println("Nenhuma ordem encontrada.");
        }
        for (OrdemServico ordem : encontradas) {
            System.out.println();
            System.out.println(ordem);
        }
    }

    
    private static void exibirDetalhesDaOrdem() {
        if (oficina.getOrdens().isEmpty()) {
            System.out.println("Nenhuma ordem cadastrada.");
            return;
        }
        OrdemServico ordem = oficina.buscarOrdemPorCodigo(entrada.lerInteiro("Código da ordem: "));
        System.out.println(ordem);
    }

    
    private static void finalizarOrdem() {
        List<OrdemServico> emExecucao = oficina.buscarOrdensPorStatus(StatusOrdem.EM_EXECUCAO);
        if (emExecucao.isEmpty()) {
            System.out.println("Não há ordens em execução.");
            return;
        }
        System.out.println("Ordens em execução:");
        listarResumos(emExecucao);
        OrdemServico ordem = oficina.buscarOrdemPorCodigo(entrada.lerInteiro("Código da ordem: "));

        ordem.finalizar();
        System.out.println("Ordem finalizada com sucesso!");
    }

    private static void listarMecanicos() {
        System.out.println("Mecânicos:");
        for (Mecanico mecanico : oficina.getMecanicos()) {
            if (mecanico.possuiBox()) {
                System.out.println(mecanico + " | Box: " + mecanico.getBox().getNumero());
            } else {
                System.out.println(mecanico + " | Sem box");
            }
        }
    }

    private static void listarBoxes() {
        System.out.println("Boxes:");
        for (Box box : oficina.getBoxes()) {
            System.out.println(box);
        }
    }

    private static void listarResumos(List<OrdemServico> ordens) {
        for (OrdemServico ordem : ordens) {
            System.out.println(ordem.resumo());
        }
    }
}
