package br.dev.mmc.cbkt.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.dev.mmc.cbkt.controller.forms.AtletaValidarForm;
import br.dev.mmc.cbkt.controller.responses.AtletaValidadoRecord;
import br.dev.mmc.cbkt.domain.Atleta;
import br.dev.mmc.cbkt.domain.record.AtletaGraduacoesRecord;
import br.dev.mmc.cbkt.service.AtletaService;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/atleta")
public class AtletaController extends CrudController<Atleta, Long> {

    private final AtletaService atletaService;

    AtletaController(AtletaService service) {
        super(service);
        this.atletaService = service;
    }

    @GetMapping("/nome/{nome}")
    public List<Atleta> findByNome(@PathVariable String nome) {
        return atletaService.findByNome(nome);
    }

    @GetMapping("/cpf/{cpf}")
    public List<Atleta> findByCpf(@PathVariable String cpf) {
        return atletaService.findByCpf(cpf);
    }

    @GetMapping("/graduacoes/nome/{nome}")
    public List<AtletaGraduacoesRecord> findGraduacoesByNome(@PathVariable String nome) {
        return atletaService.findGraduacoesByNome(nome);
    }

    @PostMapping("/validar")
    public AtletaValidadoRecord postMethodName(@RequestBody @Valid AtletaValidarForm form) {
        return atletaService.validarAtleta(form);
    }
    

}