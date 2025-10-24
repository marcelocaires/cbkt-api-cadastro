package br.dev.mmc.cbkt.controller.responses;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.dev.mmc.cbkt.domain.Atleta;
import br.dev.mmc.cbkt.domain.AtletaClube;
import br.dev.mmc.cbkt.domain.AtletaGraduacao;
import br.dev.mmc.cbkt.domain.Contato;
import br.dev.mmc.cbkt.domain.Documentos;
import br.dev.mmc.cbkt.domain.Endereco;
import br.dev.mmc.cbkt.domain.Graduacao;
import lombok.Data;

@Data
public class AtletaDTO {

    private Long id;
    private String nomeAtleta;
    private Date dataCadastro;
    private String sexo;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date dataNascimento;
    private String filiacaoMae;
    private String filiacaoPai;
    private Integer diaAnuidade;
    private Integer mesAnuidade;
    private String categoria;
    private Long codigoClube;
    private String nomeClube;
    private Long codigoClubeInicial;
    private Boolean chkArbitro;
    private Boolean chkAvaliador;
    private Boolean ativo;
    private Long codigoCategoria;
    private Boolean chkArbitroCategoria;
    private Boolean pcd;
    private String urlFoto;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date dataFaixa;
    private String nacionalidade;
    private String naturalidade;
    private String nomeMae;
    private String nomePai;
    private String observacao;
    private Documentos documentos;
    private Endereco endereco;
    private Contato contato;
    private Graduacao graduacao;
    private List<AtletaClube> clubes;
    private List<AtletaGraduacao> graduacoes;

    public AtletaDTO(Atleta atleta, List<AtletaClube> clubes) {
        this.id = atleta.getId();
        this.nomeAtleta = atleta.getNomeAtleta();
        this.dataCadastro = atleta.getDataCadastro();
        this.sexo = atleta.getSexo();
        this.dataNascimento = atleta.getDataNascimento();
        this.filiacaoMae = atleta.getFiliacaoMae();
        this.filiacaoPai = atleta.getFiliacaoPai();
        this.diaAnuidade = atleta.getDiaAnuidade();
        this.mesAnuidade = atleta.getMesAnuidade();
        this.categoria = atleta.getCategoria();
        this.codigoClube = atleta.getCodigoClube();
        this.nomeClube = atleta.getNomeClube();
        this.codigoClubeInicial = atleta.getCodigoClubeInicial();
        this.chkArbitro = atleta.getChkArbitro();
        this.chkAvaliador = atleta.getChkAvaliador();
        this.ativo = atleta.getAtivo();
        this.codigoCategoria = atleta.getCodigoCategoria();
        this.chkArbitroCategoria = atleta.getChkArbitroCategoria();
        this.pcd = atleta.getPcd();
        this.urlFoto = atleta.getUrlFoto();
        this.dataFaixa = atleta.getDataFaixa();
        this.nacionalidade = atleta.getNacionalidade();
        this.naturalidade = atleta.getNaturalidade();
        this.nomeMae = atleta.getNomeMae();
        this.nomePai = atleta.getNomePai();
        this.observacao = atleta.getObservacao();
        this.documentos = atleta.getDocumentos();
        this.endereco = atleta.getEndereco();
        this.contato = atleta.getContato();
        this.graduacoes = atleta.getGraduacoes().stream()
            .sorted((g1, g2) -> g2.getDataGraduacao().compareTo(g1.getDataGraduacao()))
            .toList().reversed();
        this.graduacao = atleta.getGraduacao();
        this.clubes = clubes;
    }
}