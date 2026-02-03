package br.dev.mmc.cbkt.domain.enums;

public enum TipoEntidade {
    CLUBE("Clube"),
    FEDERACAO("Federação"),
    CONFEDERACAO("Confederação");

    private final String descricao;

    TipoEntidade(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
