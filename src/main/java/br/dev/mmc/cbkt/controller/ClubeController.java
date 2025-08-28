package br.dev.mmc.cbkt.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.dev.mmc.cbkt.domain.Clube;
import br.dev.mmc.cbkt.service.ClubeService;

@RestController
@RequestMapping("/api/clube")
public class ClubeController extends CrudController<Clube, Long> {

    ClubeController(ClubeService service) {
        super(service);
    }
    
}