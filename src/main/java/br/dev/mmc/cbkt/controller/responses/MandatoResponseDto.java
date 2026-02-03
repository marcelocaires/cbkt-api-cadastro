package br.dev.mmc.cbkt.controller.responses;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import br.dev.mmc.cbkt.domain.Mandato;
import br.dev.mmc.cbkt.domain.enums.TipoEntidade;
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
public class MandatoResponseDto {

    private Long id;

    private TipoEntidade tipoEntidade;

    private Long entidadeId;

    private LocalDate dataInicio;

    private LocalDate dataFim;

    private Boolean ativo;

    private String descricao;

    private LocalDateTime criadoEm;

    private LocalDateTime atualizadoEm;

    private String criadoPorCpf;

    private String criadoPorNome;

    private String atualizadoPorCpf;

    private String atualizadoPorNome;

    private List<MandatoCargoDto> cargos;

    private Long versao;

    public MandatoResponseDto(Mandato mandato) {
        this.id = mandato.getId();
        this.tipoEntidade = mandato.getTipoEntidade();
        this.entidadeId = mandato.getEntidadeId();
        this.dataInicio = mandato.getDataInicio();
        this.dataFim = mandato.getDataFim();
        this.ativo = mandato.getAtivo();
        this.descricao = mandato.getDescricao();
        this.criadoEm = mandato.getCriadoEm();
        this.atualizadoEm = mandato.getAtualizadoEm();
        this.criadoPorCpf = mandato.getCriadoPorCpf();
        this.criadoPorNome = mandato.getCriadoPorNome();
        this.atualizadoPorCpf = mandato.getAtualizadoPorCpf();
        this.atualizadoPorNome = mandato.getAtualizadoPorNome();
        this.versao = mandato.getVersao();
    }

    public Mandato toEntity() {
        Mandato mandato = new Mandato();
        mandato.setId(this.id);
        mandato.setTipoEntidade(this.tipoEntidade);
        mandato.setEntidadeId(this.entidadeId);
        mandato.setDataInicio(this.dataInicio);
        mandato.setDataFim(this.dataFim);
        mandato.setAtivo(this.ativo);
        mandato.setDescricao(this.descricao);
        mandato.setCriadoEm(this.criadoEm);
        mandato.setAtualizadoEm(this.atualizadoEm);
        mandato.setCriadoPorCpf(this.criadoPorCpf);
        mandato.setCriadoPorNome(this.criadoPorNome);
        mandato.setAtualizadoPorCpf(this.atualizadoPorCpf);
        mandato.setAtualizadoPorNome(this.atualizadoPorNome);
        mandato.setVersao(this.versao);
        return mandato;
    }
}
