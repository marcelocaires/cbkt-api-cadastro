package br.dev.mmc.cbkt.domain.enums;

public enum GraduacaoCorNomeEnum {

    BRANCA("Branca"),
    CINZA("Cinza"),
    AMARELA("Amarela"),
    LARANJA("Laranja"),
    VERMELHA("Vermelha"),
    ROXA("Roxa"),
    VERDE("Verde"),
    AZUL_CLARA("Azul Clara"),
    AZUL_ESCURA("Azul Escura"),
    AZUL("Azul"),
    MARROM("Marrom"),
    PRETA("Preta");

    private final String descricao;

    GraduacaoCorNomeEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
