package br.dev.mmc.cbkt.controller.responses;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.dev.mmc.cbkt.domain.Clube;
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
public class ClubeComMandatosDto {

    private Long id;

    private String nome;

    private String abreviatura;

    private String classificacao;

    private String cnpj;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dataFundacao;

    private ContatoDto contato;

    private EnderecoDto endereco;

    private ClubeDiretoriaDto diretoria;

    @Builder.Default
    private Set<MandatoResponseDto> mandatos = new LinkedHashSet<>();

    @Builder.Default
    private List<MandatoCargoDto> diretoriaAtiva = new ArrayList<>();

    private MandatoResponseDto mandatoAtivo;

    @Builder.Default
    private List<ClubeInstrutorDto> instrutores = new ArrayList<>();

    @Builder.Default
    private List<AtletaClubeDto> atletas = new ArrayList<>();

    // Estatísticas
    private Integer totalAtletas;
    private Integer totalInstrutores;
    private Integer totalMandatos;
    private Integer totalAtletasTransferidos;
    private Integer totalAtletasArbitros;
    private Integer totalAtletasAvaliadores;

    public ClubeComMandatosDto(Clube clube, Set<MandatoResponseDto> mandatos) {
        this.id = clube.getId();
        this.nome = clube.getNome();
        this.abreviatura = clube.getAbreviatura();
        this.classificacao = clube.getClassificacao();
        this.cnpj = clube.getCnpj();
        this.dataFundacao = clube.getDataFundacao();
        
        // Mapear Contato
        if (clube.getContato() != null) {
            this.contato = ContatoDto.builder()
                .email(clube.getContato().getEmail())
                .telefone(clube.getContato().getTelefone())
                .build();
        }
        
        // Mapear Endereco
        if (clube.getEndereco() != null) {
            this.endereco = EnderecoDto.builder()
                .logradouro(clube.getEndereco().getLogradouro())
                .numero(clube.getEndereco().getNumero())
                .complemento(clube.getEndereco().getComplemento())
                .bairro(clube.getEndereco().getBairro())
                .cidade(clube.getEndereco().getCidade())
                .estado(clube.getEndereco().getEstado())
                .uf(clube.getEndereco().getUf())
                .cep(clube.getEndereco().getCep())
                .build();
        }
        
        // Mapear ClubeDiretoria
        if (clube.getDiretoria() != null) {
            this.diretoria = ClubeDiretoriaDto.builder()
                .presidente(clube.getDiretoria().getPresidente())
                .directorTecnico(clube.getDiretoria().getDiretorTecnico())
                .responsavel(clube.getDiretoria().getResponsavel())
                .build();
        }
        
        this.mandatos = mandatos != null ? mandatos : new LinkedHashSet<>();
        this.mandatoAtivo = mandatos.stream()
            .filter(MandatoResponseDto::getAtivo)
            .findFirst()
            .orElse(null);
        this.diretoriaAtiva = new ArrayList<>();
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ContatoDto {
        private String email;
        private String telefone;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class EnderecoDto {
        private String logradouro;
        private String numero;
        private String complemento;
        private String bairro;
        private String cidade;
        private String estado;
        private String uf;
        private String cep;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ClubeDiretoriaDto {
        private String presidente;
        private String directorTecnico;
        private String responsavel;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ClubeInstrutorDto {
        private Long id;
        private Long atletaId;
        private String atletaNome;
        private LocalDate dataInicio;
        private LocalDate dataFim;
        private Boolean ativo;
        private String observacao;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AtletaClubeDto {
        private Long id;
        private Long atletaId;
        private String atletaNome;
        private Date dataAdmissao;
        private Date dataSaida;
        private Boolean transferido;
        private Long clubeOrigemId;
        private String clubeOrigemNome;
    }
}
