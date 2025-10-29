package br.dev.mmc.cbkt.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.dev.mmc.cbkt.controller.forms.AtletaValidarForm;
import br.dev.mmc.cbkt.controller.responses.AtletaDTO;
import br.dev.mmc.cbkt.controller.responses.AtletaValidadoRecord;
import br.dev.mmc.cbkt.domain.Atleta;
import br.dev.mmc.cbkt.domain.AtletaGraduacao;
import br.dev.mmc.cbkt.service.AtletaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/atleta")
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
            - Parâmetros opcionais: page (número da página, padrão 0), size (tamanho da página, padrão 10), sort (campo de ordenação).
            - Exemplo: /api/atleta/page?page=0&size=10&sort=nomeAtleta,asc
            - Esse endpoint exige autenticação.
        """
    )
    public Page<Atleta> getAllPage(Pageable pageable) {
        return atletaService.getAllPage(pageable);
    }

    @GetMapping("/id/{id}")
    public AtletaDTO findById(@PathVariable Long id) {
        return atletaService.findById(id);
    }

    @GetMapping("/nome/{nome}")
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
    public List<AtletaGraduacao> findGraduacoesById(@PathVariable Long id) {
        return atletaService.findGraduacoesById(id);
    }

    @PostMapping("/validar")
    public AtletaValidadoRecord postMethodName(@RequestBody @Valid AtletaValidarForm form) {
        return atletaService.validarAtleta(form);
    }
}