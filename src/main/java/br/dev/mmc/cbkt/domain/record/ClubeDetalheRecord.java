package br.dev.mmc.cbkt.domain.record;

import java.util.List;

public record ClubeDetalheRecord(Long id, String nomeClube, List<AtletaResumoRecord> atletas) {}