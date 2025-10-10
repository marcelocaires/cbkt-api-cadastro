package br.dev.mmc.cbkt.domain.enums;

public enum GraduacaoCorHexEnum {

    BRANCA("#FFFFFF"),
    CINZA("#A9A9A9"),
    AMARELA("#FFD700"),
    LARANJA("#FFA500"),
    VERMELHA("#FF0000"),
    ROXA("#800080"),
    VERDE("#008000"),
    AZUL_CLARA("#00BFFF"),
    AZUL("#0000FF"),
    AZUL_ESCURA("#00008B"),
    MARROM("#8B4513"),
    PRETA("#000000");

    private final String codigoHex;

    GraduacaoCorHexEnum(String codigoHex) {
        this.codigoHex = codigoHex;
    }

    public String getCodigoHex() {
        return codigoHex;
    }

    @Override
    public String toString() {
        return codigoHex;
    }
}
