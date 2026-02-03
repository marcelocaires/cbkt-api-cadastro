package br.dev.mmc.cbkt.domain.support;

public final class AuditoriaContext {
    private static final ThreadLocal<String> CPF = new ThreadLocal<>();
    private static final ThreadLocal<String> NOME = new ThreadLocal<>();

    private AuditoriaContext() {}

    public static void setCpfAtual(String cpfSemMascara) {
        CPF.set(cpfSemMascara);
    }

    public static void setNomeAtual(String nome) {
        NOME.set(nome);
    }

    public static String getCpfAtual() {
        return CPF.get();
    }

    public static String getNomeAtual() {
        return NOME.get();
    }

    public static void clear() {
        CPF.remove();
        NOME.remove();
    }
}
