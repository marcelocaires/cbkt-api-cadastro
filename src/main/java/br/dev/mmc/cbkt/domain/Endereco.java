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
public class Endereco {

    @Column(name = "LOGRADOURO", length = 120)
    private String logradouro;

    @Column(name = "NUMERO", length = 15)
    private String numero;

    @Column(name = "BAIRRO", length = 80)
    private String bairro;

    @Column(name = "COMPLEMENTO", length = 80)
    private String complemento;

    @Column(name = "CIDADE", length = 80)
    private String cidade;

    @Column(name = "ESTADO", length = 2)
    private String estado;

    @Column(name = "CEP", length = 10)
    private String cep;

    @Column(name = "UF", length = 2)
    private String uf; // alguns lugares usam UF separado de ESTADO
}
