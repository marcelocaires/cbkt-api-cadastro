package br.dev.mmc.cbkt.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.dev.mmc.cbkt.config.exceptions.ResourceNotFoundException;
import br.dev.mmc.cbkt.domain.Atleta;
import br.dev.mmc.cbkt.repository.AtletaRepository;

@Service
public class AtletaService extends CrudServiceImpl<Atleta, Long> {

    private final AtletaRepository atletaRepository;

    public AtletaService(AtletaRepository repo, AtletaRepository atletaRepository) {
        super(repo);
        this.atletaRepository = atletaRepository;
    }

    public Atleta findByCpf(String cpf) {
        return atletaRepository.findByCpfAtleta(cpf)
            .orElseThrow(() -> new ResourceNotFoundException("Atleta com CPF " + cpf + " não encontrado"));
    }

    public List<Atleta> findByNome(String nome) {
        return atletaRepository.searchByNome(nome).isEmpty() ? null : atletaRepository.searchByNome(nome);
    }
}
