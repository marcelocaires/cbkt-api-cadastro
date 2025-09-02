package br.dev.mmc.cbkt.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.dev.mmc.cbkt.domain.Graduacao;
import br.dev.mmc.cbkt.service.GraduacaoService;

@RestController
@RequestMapping("/api/graduacao")
public class GraduacaoController extends CrudController<Graduacao, Long> {

    private final GraduacaoService graduacaoService;

    GraduacaoController(GraduacaoService service) {
        super(service);
        this.graduacaoService = service;
    }
}