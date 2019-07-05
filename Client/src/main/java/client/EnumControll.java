package client;

enum Controll {
    CREATE("CREATE"), READ("READ"), UPDATE("UPDATE"), DELETE("DELETE");
    private String descricao;

    Controll(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}
