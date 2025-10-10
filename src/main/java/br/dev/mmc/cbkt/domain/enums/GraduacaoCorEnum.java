package br.dev.mmc.cbkt.domain.enums;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public enum GraduacaoCorEnum {

    BRANCA(1, "Branca", "#FFFFFF"),
    CINZA(2, "Cinza", "#A9A9A9"),
    AMARELA(3, "Amarela", "#FFD700"),
    LARANJA(4, "Laranja", "#FFA500"),
    VERMELHA(5, "Vermelha", "#FF0000"),
    ROXA(6, "Roxa", "#800080"),
    VERDE(7, "Verde", "#008000"),
    AZUL_CLARA(8, "Azul Clara", "#00BFFF"),
    AZUL(9, "Azul", "#0000FF"),
    AZUL_ESCURA(10, "Azul Escura", "#00008B"),
    MARROM(11, "Marrom", "#8B4513"),
    PRETA(12, "Preta", "#000000");

    private final int indice;
    private final String nome;
    private final String codigo;

    GraduacaoCorEnum(int indice, String nome, String codigo) {
        this.indice = indice;
        this.nome = nome;
        this.codigo = codigo;
    }

    public int getIndice() {
        return indice;
    }

    public String getNome() {
        return nome;
    }

    public String getCodigo() {
        return codigo;
    }

    @Override
    public String toString() {
        return indice + " - " + nome + " (" + codigo + ")";
    }

    /** 🔍 Busca pelo nome da cor (case-insensitive). */
    public static GraduacaoCorEnum getByNome(String nome) {
        if (nome == null || nome.isBlank()) return null;
        for (GraduacaoCorEnum cor : values()) {
            if (cor.nome.equalsIgnoreCase(nome.trim())) {
                return cor;
            }
        }
        return null;
    }

    /** 🎨 Busca pelo código da cor (case-insensitive). */
    public static GraduacaoCorEnum getByCodigo(String codigo) {
        if (codigo == null || codigo.isBlank()) return null;
        for (GraduacaoCorEnum cor : values()) {
            if (cor.codigo.equalsIgnoreCase(codigo.trim())) {
                return cor;
            }
        }
        return null;
    }

    /** 🔢 Busca pelo índice da cor. */
    public static GraduacaoCorEnum getByIndice(int indice) {
        for (GraduacaoCorEnum cor : values()) {
            if (cor.indice == indice) {
                return cor;
            }
        }
        return null;
    }

    public static List<Map<String, Object>> listarTodos() {
        List<Map<String, Object>> lista = new ArrayList<>();
        for (GraduacaoCorEnum cor : values()) {
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("indice", cor.indice);
            item.put("nome", cor.nome);
            item.put("codigo", cor.codigo);
            lista.add(item);
        }
        return lista;
    }
}
