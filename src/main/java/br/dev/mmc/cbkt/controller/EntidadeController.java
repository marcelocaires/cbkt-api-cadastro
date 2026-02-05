package br.dev.mmc.cbkt.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.dev.mmc.cbkt.controller.responses.ClubeComMandatosDto;
import br.dev.mmc.cbkt.domain.Clube;
import br.dev.mmc.cbkt.service.EntidadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/entidade")
@Tag(name = "Entidade")
public class EntidadeController{
    private final EntidadeService service;

    EntidadeController(EntidadeService service) {
        this.service = service;
    }

    @GetMapping("/{tipo}")
    @Operation(
        summary = "Retorna entidades.",
        description = """
            Retorna uma lista de entidades.
            - Parâmetro obrigatório: tipo de entidade: F(Federação), C(Confederação).
            - Filtro opcional via parâmetro 'filter'.
            - Exemplo: /api/clube/page?page=0&size=10&sort=nome,asc
            - Esse endpoint exige autenticação.
        """
    )
    public List<Clube> read(
        @PathVariable String tipo,
        @RequestParam(required = false) String filter){
        if(tipo .equalsIgnoreCase("F")) {
            return service.getFederacoesFilter(filter);
        }
        if(tipo.equalsIgnoreCase("C")) {
            return service.getConfederacao();
        }
        throw new IllegalArgumentException("Classificação inválida: " + tipo);
    }

    @GetMapping("/page/{tipo}")
    @Operation(
        summary = "Retorna entidades com paginação.",
        description = """
            Retorna uma lista paginada de entidades.
            - Parâmetro obrigatório: tipo de entidade: F(Federação), C(Confederação).
            - Parâmetros opcionais: page (número da página, padrão 0), size (tamanho da página, padrão 10), sort (campo de ordenação, padrão nome).
            - Filtro opcional via parâmetro 'filter'.
            - Exemplo: /api/clube/page?page=0&size=10&sort=nome,asc
            - Esse endpoint exige autenticação.
        """
    )
    public Page<Clube> getAllPage(
        @PathVariable String tipo,
        @RequestParam(required = false) String filter,
        Pageable pageable){
        if(tipo.equalsIgnoreCase("F")) {
            return service.getFederacoesPageFilter(pageable, filter);
        }
        if(tipo.equalsIgnoreCase("C")) {
            return service.getConfederacaoPage();
        }
        throw new IllegalArgumentException("Classificação inválida: " + tipo);
    }

    @GetMapping("/detalhe/{id}")
    public ResponseEntity<ClubeComMandatosDto> buscarClubeComMandatosById(@PathVariable Long id) {
        var detalhe = service.getEntidadeCompletaById(id);
        return ResponseEntity.ok(detalhe);
    }    

    @PostMapping("/{tipo}")
    public String postMethodName(
        @PathVariable String tipo, 
        @RequestBody String entity) {        
        return entity;
    }
    
}