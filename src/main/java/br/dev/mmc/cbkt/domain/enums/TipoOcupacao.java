package br.dev.mmc.cbkt.domain.enums;

public enum TipoOcupacao {
    TITULAR("Titular"),
    SUPLENTE("Suplente");

    private final String descricao;

    TipoOcupacao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
