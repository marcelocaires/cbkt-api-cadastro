package br.dev.mmc.cbkt.domain;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
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
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "ATLETA")
public class Atleta {

    @Id
    @Column(name = "CODIGOATLETA")
    private Long id;

    @Column(name = "NOMEATLETA", length = 120, nullable = false)
    private String nomeAtleta;

    @Column(name = "DATACADASTRO")
    private LocalDate dataCadastro;

    @Column(name = "CPFATLETA", length = 14)
    private String cpfAtleta;

    @Column(name = "RG_NUMERO", length = 20)
    private String rgNumero;

    @Column(name = "RG_ORGAO", length = 20)
    private String rgOrgao;

    @Column(name = "RG_ESTADO", length = 2)
    private String rgEstado;

    @Column(name = "CERTIDAONASCIMENTO", length = 50)
    private String certidaoNascimento;

    @Column(name = "SEXO", length = 1)
    private String sexo;

    @Column(name = "DATANASCIMENTO")
    private LocalDate dataNascimento;

    @Column(name = "FILIACAO_MAE", length = 120)
    private String filiacaoMae;

    @Column(name = "FILIACAO_PAI", length = 120)
    private String filiacaoPai;

    @Column(name = "EMAIL", length = 120)
    private String email;

    @Column(name = "DIAANUIDADE")
    private Integer diaAnuidade;

    @Column(name = "MESANUIDADE")
    private Integer mesAnuidade;

    @Column(name = "CATEGORIA", length = 50)
    private String categoria;

    @Column(name = "CODIGOGRADUACAO")
    private Long codigoGraduacao;

    @Column(name = "DESCRICAOGRADUACAO", length = 50)
    private String descricaoGraduacao;

    @Column(name = "CODIGOCLUBE")
    private Long codigoClube;

    @Column(name = "NOMECLUBE", length = 120)
    private String nomeClube;

    @Column(name = "CODIGOCLUBEINICIAL")
    private Long codigoClubeInicial;

    @Column(name = "CHK_ARBITRO")
    private Boolean chkArbitro;

    @Column(name = "CHK_AVALIADOR")
    private Boolean chkAvaliador;

    @Column(name = "ATIVO")
    private Boolean ativo;

    @Column(name = "CODIGOCATEGORIA")
    private Long codigoCategoria;

    @Column(name = "CHK_ARBITROCATEGORIA")
    private Boolean chkArbitroCategoria;

    @Column(name = "PCD")
    private Boolean pcd;

    @Column(name = "URL_FOTO", length = 255)
    private String urlFoto;

    @Column(name = "CPF", length = 14)
    private String cpf;

    @Column(name = "DATAFAIXA")
    private LocalDate dataFaixa;

    @Column(name = "GRADUACAO", length = 30)
    private String graduacao;

    @Column(name = "NACIONALIDADE", length = 50)
    private String nacionalidade;

    @Column(name = "NATURALIDADE", length = 80)
    private String naturalidade;

    @Column(name = "NOMEMAE", length = 120)
    private String nomeMae;

    @Column(name = "NOMEPAI", length = 120)
    private String nomePai;

    @Column(name = "OBSERVACAO", length = 255)
    private String observacao;

    @Column(name = "RG", length = 20)
    private String rg;
    
    @Embedded
    private Endereco endereco;
}
