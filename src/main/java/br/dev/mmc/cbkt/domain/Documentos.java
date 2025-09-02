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
public class Documentos {

    @Column(name = "CPFATLETA", length = 14)
    private String cpf;

    @Column(name = "RG_NUMERO", length = 20)
    private String rg;

    @Column(name = "RG_ORGAO", length = 20)
    private String rgOrgao;

    @Column(name = "RG_ESTADO", length = 2)
    private String rgEstado;

    @Column(name = "CERTIDAONASCIMENTO", length = 50)
    private String certidaoNascimento;
}
