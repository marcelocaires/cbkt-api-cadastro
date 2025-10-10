package br.dev.mmc.cbkt.domain.enums;

public enum GraduacaoGrauEnum {

    KYU_10("10º KYU"),
    KYU_9("9º KYU"),
    KYU_8("8º KYU"),
    KYU_7("7º KYU"),
    KYU_6("6º KYU"),
    KYU_5("5º KYU"),
    KYU_4("4º KYU"),
    KYU_3("3º KYU"),
    KYU_2("2º KYU"),
    KYU_1("1º KYU"),
    DAN_1("1º DAN"),
    DAN_2("2º DAN"),
    DAN_3("3º DAN"),
    DAN_4("4º DAN"),
    DAN_5("5º DAN"),
    DAN_6("6º DAN"),
    DAN_7("7º DAN"),
    DAN_8("8º DAN"),
    DAN_9("9º DAN"),
    DAN_10("10º DAN");

    private final String titulo;

    GraduacaoGrauEnum(String titulo) {
        this.titulo = titulo;
    }

    public String getTitulo() {
        return titulo;
    }

    @Override
    public String toString() {
        return titulo;
    }
}
