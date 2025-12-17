package br.dev.mmc.cbkt.controller.responses;

import br.dev.mmc.cbkt.domain.Atleta;
import lombok.Data;

@Data
public class AtletaShortDTO {

    private Long id;
    private String nomeAtleta;
    private String nomeClube;
    private String graduacaoDescricao;
    private String graduacaoCorHex;

    public AtletaShortDTO(Atleta atleta) {
        this.id = atleta.getId();
        this.nomeAtleta = atleta.getNomeAtleta();
        if(atleta.getNomeClube() != null) {
            this.nomeClube = atleta.getNomeClube();
        }
        if(atleta.getGraduacao() != null) {
            this.graduacaoDescricao = atleta.getGraduacao().getDescricaoGraduacao();
            this.graduacaoCorHex = atleta.getGraduacao().getCorHex();
        }
    }
}