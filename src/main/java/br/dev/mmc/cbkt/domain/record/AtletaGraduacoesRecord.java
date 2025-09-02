package br.dev.mmc.cbkt.domain.record;

import java.util.Set;

import br.dev.mmc.cbkt.domain.AtletaGraduacao;

public record AtletaGraduacoesRecord(
    Set<AtletaGraduacao> graduacoes
) {}
