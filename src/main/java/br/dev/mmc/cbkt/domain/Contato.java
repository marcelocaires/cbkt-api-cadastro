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
public class Contato {
    @Column(name = "EMAIL", length = 120)
    private String email;

    @Column(name = "TELEFONE", length = 20)
    private String telefone;
}
