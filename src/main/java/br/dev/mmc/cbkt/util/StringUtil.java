package br.dev.mmc.cbkt.util;

public class StringUtil {
    public static String convertNome(String nome) {
        return "%" + nome
            .replace("\"", "\"")
            .replace("%", "\\%")
            .replace("_", "\\_")
            .toUpperCase()
            + "%";
    }
}
