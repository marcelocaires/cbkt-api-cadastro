package br.dev.mmc.cbkt.domain.record;

import java.time.LocalDate;

public record AtletaGraduacaoRecord(
    Long sequencial,          // id do vínculo ATLETA_GRADUACAO
    Long codigoGraduacao,     // GRADUACAO.id
    String descricaoGraduacao,
    LocalDate dataGraduacao,
    Integer notaGraduacao
) {}
