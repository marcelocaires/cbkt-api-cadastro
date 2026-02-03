package br.dev.mmc.cbkt.domain.enums;

public enum TipoVinculo {
    ELEITO("Eleito em assembleia"),
    NOMEADO("Nomeado pela diretoria"),
    CONTRATADO("Funcionário contratado"),
    PRO_TEMPORE("Ocupação temporária");

    private final String descricao;

    TipoVinculo(String descricao) {
        this.descricao = descricao;
    }

    public String nome() {
        return this.name();
    }

    public String getDescricao() {
        return descricao;
    }
}
