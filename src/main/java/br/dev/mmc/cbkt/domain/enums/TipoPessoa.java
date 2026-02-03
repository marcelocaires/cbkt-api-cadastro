package br.dev.mmc.cbkt.domain.enums;

public enum TipoPessoa {
    ATLETA("Atleta"),
    INTEGRANTE("Integrante"),
    AMBOS("Atleta e Integrante");

    private final String descricao;

    TipoPessoa(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
