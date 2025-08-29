package br.dev.mmc.cbkt.domain;


import lombok.*;
import jakarta.persistence.*;


@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
@Embeddable
public class CCustoId {
    @Column(name = "CODIGOCLUBE")
    private Long codigoClube;

    @Column(name = "COD_CCUSTO")
    private String codigoCcusto;
}
