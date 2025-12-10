package br.dev.mmc.cbkt.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.dev.mmc.cbkt.controller.forms.AtletaClubeForm;
import br.dev.mmc.cbkt.controller.forms.AtletaClubeTransferirForm;
import br.dev.mmc.cbkt.controller.forms.AtletaValidarForm;
import br.dev.mmc.cbkt.controller.responses.AtletaDTO;
import br.dev.mmc.cbkt.controller.responses.AtletaShortDTO;
import br.dev.mmc.cbkt.controller.responses.AtletaValidadoRecord;
import br.dev.mmc.cbkt.domain.Atleta;
import br.dev.mmc.cbkt.domain.AtletaClube;
import br.dev.mmc.cbkt.domain.AtletaGraduacao;
import br.dev.mmc.cbkt.service.AtletaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/atleta")
public class AtletaController extends CrudController<Atleta, Long> {

    private final AtletaService atletaService;

    AtletaController(AtletaService service) {
        super(service);
        this.atletaService = service;
    }

    @GetMapping("/page")
    @Operation(
        summary = "Retorna atletas com paginação.",
        description = """
            Retorna uma lista paginada de atletas.
            - Parâmetros opcionais: page (número da página, padrão 0), size (tamanho da página, padrão 10), sort (campo de ordenação, padrão nomeAtleta).
            - Filtro opcional por nome, graduação ou clube via parâmetro 'filter'.
            - Exemplo: /api/atleta/page?page=0&size=10&sort=nomeAtleta,asc
            - Esse endpoint exige autenticação.
        """
    )
    public Page<AtletaShortDTO> getAllPage(
        @RequestParam(required = false) String filter,
        Pageable pageable){
        return atletaService.filterPageByNGC(pageable, filter)
            .map(AtletaShortDTO::new);
    }

    @GetMapping("/id/{id}")
    @Operation(
        summary = "Retorna atleta pelo ID.",
        description = """
            Retorna os detalhes do atleta correspondente ao ID fornecido.
            - Se o atleta for encontrado → retorna 200 com os detalhes do atleta.
            - Se o atleta não for encontrado → retorna 404.
            - Esse endpoint exige autenticação.
        """
    )
    public AtletaDTO findById(@PathVariable Long id) {
        return atletaService.findById(id);
    }

    @GetMapping("/nome/{nome}")
    @Operation(
        summary = "Retorna atleta pelo nome.",
        description = """
            Retorna os detalhes do atleta correspondente ao nome fornecido.
            - Se o atleta for encontrado → retorna 200 com os detalhes do atleta.
            - Se o atleta não for encontrado → retorna 404.
            - Esse endpoint exige autenticação.
        """
    )
    public List<Atleta> findByNome(@PathVariable String nome) {
        return atletaService.findByNome(nome);
    }

    @GetMapping("/cpf/{cpf}")
    @Operation(
        summary = "Retorna atletas pelo CPF.",
        description = """
            Retorna uma lista de atletas que correspondem ao CPF fornecido.
            - Se o CPF for encontrado → retorna 200 com a lista de atletas.
            - Se o CPF não for encontrado → retorna 404.
            - Esse endpoint exige autenticação.
        """
    )
    public List<Atleta> findByCpf(
        @Parameter(description = "CPF do atleta", example = "123.456.789-00")
        @PathVariable String cpf) {
        return atletaService.findByCpf(cpf);
    }

    @GetMapping("/graduacoes/id/{id}")
    @Operation(
        summary = "Retorna as graduações de um atleta pelo ID.",
        description = """
            Retorna uma lista das graduações associadas ao atleta identificado pelo ID fornecido.
            - Se o atleta for encontrado → retorna 200 com a lista de graduações.
            - Se o atleta não for encontrado → retorna 404.
            - Esse endpoint exige autenticação.
        """
    )
    public List<AtletaGraduacao> findGraduacoesById(@PathVariable Long id) {
        return atletaService.findGraduacoesById(id);
    }

    @PostMapping("/validar")
    @Operation(
        summary = "Valida os dados de um atleta.",
        description = """
            Valida os dados fornecidos para um atleta.
            - Retorna os dados validados do atleta.
            - Esse endpoint exige autenticação.
        """
    )
    public AtletaValidadoRecord postMethodName(@RequestBody @Valid AtletaValidarForm form) {
        return atletaService.validarAtleta(form);
    }

    @PostMapping("/clube")
    @Operation(
        summary = "Adiciona um clube a um atleta.",
        description = """
            Adiciona um clube ao atleta com base nos dados fornecidos.
            - Retorna o histórico de clubes do atleta.
            - Esse endpoint exige autenticação.
        """
    )
    public List<AtletaClube> adicionarClube(@RequestBody @Valid AtletaClubeForm form) {
        return atletaService.adicionarClube(form);
    }

    @PostMapping("/clube/transferir")
    @Operation(
        summary = "Transfere um atleta de clube.",
        description = """
            Transfere um atleta de clube com base nos dados fornecidos.
            - Retorna o histórico de clubes do atleta.
            - Esse endpoint exige autenticação.
        """
    )
    public List<AtletaClube> transferirClube(@RequestBody @Valid AtletaClubeTransferirForm form) {
        return atletaService.transferirClube(form);
    }

    @DeleteMapping("/{atletaId}/clube/{clubeId}")
    @Operation(
        summary = "Remove um clube de um atleta.",
        description = """
            Remove um clube do atleta com base nos dados fornecidos.
            - Retorna o histórico de clubes do atleta.
            - Esse endpoint exige autenticação.
        """
    )
    public List<AtletaClube> removerClube(@PathVariable Long atletaId, @PathVariable Long clubeId) {
        return atletaService.removerClube(atletaId, clubeId);
    }
}