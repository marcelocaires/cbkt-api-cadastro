package br.dev.mmc.cbkt.domain.comparators;

import java.util.Comparator;

import br.dev.mmc.cbkt.domain.AtletaGraduacao;

/**
 * Comparators para a entidade Graduacao.
 * Fornece diferentes estratégias de ordenação reutilizáveis.
 */
public class AtletaGraduacaoComparators {

    /**
     * Comparator que ordena graduações por grau.
     * KYU vem antes de DAN, e dentro de cada categoria a ordem é crescente
     * (10º KYU até 1º KYU, depois 1º DAN até 10º DAN).
     * Graduações sem grau definido vêm por último.
     */
    public static final Comparator<AtletaGraduacao> BY_GRAU = Comparator
            .comparing(ag -> ag.getGraduacao().getGrau(), Comparator.nullsLast(Comparator.naturalOrder()));

    /**
     * Comparator reverso que ordena graduações por grau em ordem decrescente.
     * DAN vem primeiro, seguido de KYU.
     * Graduações sem grau definido vêm por último.
     */
    public static final Comparator<AtletaGraduacao> BY_GRAU_DESC = BY_GRAU.reversed();

    private AtletaGraduacaoComparators() {
        // Classe utilitária, não deve ser instanciada
    }
}
