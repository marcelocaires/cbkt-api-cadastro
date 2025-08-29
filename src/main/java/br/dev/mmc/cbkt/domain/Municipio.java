package br.dev.mmc.cbkt.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@EqualsAndHashCode(of = "id")
@Entity @Table(name = "MUNICIPIO")
public class Municipio {
    @Id
    @Column(name = "COD_MUNICIPIO")
    private Long id;

    @Column(name = "NME_MUNICIPIO", length = 100)
    private String nome;

    @Column(name = "SLG_ESTADO", length = 2)
    private String uf;
}
