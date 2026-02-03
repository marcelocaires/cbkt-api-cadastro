package br.dev.mmc.cbkt.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class ClubeDiretoria {
    @Column(name = "RESPONSAVEL", length = 120)
    private String responsavel;

    @Column(name = "PRESIDENTE", length = 120)
    private String presidente;

    @Column(name = "DIRETORTECNICO", length = 120)
    private String diretorTecnico;
}
