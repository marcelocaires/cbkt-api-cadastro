package br.dev.mmc.cbkt.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EqualsAndHashCode(of = "id")
@Table(name = "CLUBE")
public class Clube{

    @Id
    @Column(name = "CODIGOCLUBE")
    private Long id;

    @Column(name = "NOMECLUBE", nullable = false, length = 120)
    private String nome;

    @Column(name = "ABREVIATURA", length = 30)
    private String abreviatura;

    @Column(name = "CLASSIFICACAO", length = 30)
    private String classificacao;

    @Column(name = "CIDADE", length = 80)
    private String cidade;

    @Column(name = "CEP", length = 10)
    private String cep;

    @Column(name = "LOGRADOURO", length = 120)
    private String logradouro;

    @Column(name = "NUMERO", length = 15)
    private String numero;

    @Column(name = "BAIRRO", length = 80)
    private String bairro;

    @Column(name = "COMPLEMENTO", length = 80)
    private String complemento;

    @Column(name = "TELEFONE", length = 30)
    private String telefone;

    @Column(name = "EMAIL", length = 120)
    private String email;

    @Column(name = "RESPONSAVEL", length = 120)
    private String responsavel;

    @Column(name = "DATAFUNDACAO")
    private LocalDate dataFundacao;

    @Column(name = "DIMENSAODE", length = 30)
    private String dimensaoDe;

    @Column(name = "CNPJ", length = 18)
    private String cnpj;

    @Column(name = "AN_MEN_DEBITO_1AV")
    private Integer anMenDebito1Av;

    @Column(name = "AN_MEN_DEBITO_2AV")
    private Integer anMenDebito2Av;

    @Column(name = "AN_MEN_DEBITO_3AV")
    private Integer anMenDebito3Av;

    @Column(name = "VALOR_MENSALIDADE_CLUBE", precision = 15, scale = 2)
    private BigDecimal valorMensalidadeClube;

    @Column(name = "MASCARA_CONTA", length = 30)
    private String mascaraConta;

    @Column(name = "CTA_DES_DESCONTO", length = 30)
    private String ctaDesconto;

    @Column(name = "CTA_DES_JUROS", length = 30)
    private String ctaJuros;

    @Column(name = "CTA_DES_MULTA", length = 30)
    private String ctaMulta;

    @Column(name = "CTA_REC_DESCONTO", length = 30)
    private String ctaRecDesconto;

    @Column(name = "CTA_REC_JUROS", length = 30)
    private String ctaRecJuros;

    @Column(name = "CTA_REC_MULTA", length = 30)
    private String ctaRecMulta;

    @Column(name = "PRESIDENTE", length = 120)
    private String presidente;

    @Column(name = "DIRETORTECNICO", length = 120)
    private String diretorTecnico;
}
