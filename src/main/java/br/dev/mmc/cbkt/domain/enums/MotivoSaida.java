package br.dev.mmc.cbkt.domain.enums;

public enum MotivoSaida {
    RENUNCIA("Renúncia"),
    DESTITUICAO("Destituição"),
    FALECIMENTO("Falecimento"),
    TERMINO_MANDATO("Término do mandato"),
    DEMISSAO("Demissão"),
    OUTROS("Outros");

    private final String descricao;

    MotivoSaida(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
