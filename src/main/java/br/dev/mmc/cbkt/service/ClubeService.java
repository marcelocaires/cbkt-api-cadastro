package br.dev.mmc.cbkt.service;

import org.springframework.stereotype.Service;

import br.dev.mmc.cbkt.domain.Clube;
import br.dev.mmc.cbkt.repository.ClubeRepository;

@Service
public class ClubeService extends CrudServiceImpl<Clube, Long> {

    public ClubeService(ClubeRepository repo) {
        super(repo);
    }
}
