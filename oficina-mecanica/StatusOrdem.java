


public enum StatusOrdem {
    ABERTA("Aberta"),
    EM_EXECUCAO("Em execução"),
    FINALIZADA("Finalizada");

    private final String descricao;

    StatusOrdem(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
