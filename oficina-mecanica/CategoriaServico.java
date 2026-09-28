




public enum CategoriaServico {
    MECANICA("Mecânica geral"),
    ELETRICA("Elétrica"),
    FUNILARIA("Funilaria e pintura"),
    ALINHAMENTO("Alinhamento e balanceamento");

    private final String descricao;

    CategoriaServico(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
