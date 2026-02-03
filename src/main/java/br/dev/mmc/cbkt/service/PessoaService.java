package br.dev.mmc.cbkt.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.dev.mmc.cbkt.controller.responses.PessoaDto;
import br.dev.mmc.cbkt.domain.Atleta;
import br.dev.mmc.cbkt.domain.Pessoa;
import br.dev.mmc.cbkt.domain.enums.TipoPessoa;
import br.dev.mmc.cbkt.repository.PessoaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PessoaService {

    private final AtletaService atletaService;
    private final PessoaRepository pessoaRepository;

    /**
     * Obtém pessoa por CPF
     */
    public Pessoa obterPessoaPorCpf(String cpf) {
        cpf=cpf.replaceAll("[^\\d]", ""); // remover máscara
        Pessoa pessoa = pessoaRepository.findByCpf(cpf).orElse(null);
        if(pessoa == null) {
            Atleta atleta = atletaService.getByCpf(cpf);
            if(atleta != null) {
               pessoa= criarPessoaPorAtleta(atleta);
            }
        }
        return pessoa;
    }

    private Pessoa criarPessoaPorAtleta(Atleta atleta) {
        Pessoa pessoa = Pessoa.builder()
            .nome(atleta.getNomeAtleta())
            .cpf(atleta.getDocumentos().getCpf().replaceAll("[^\\d]", ""))
            .dataNascimento(atleta.getDataNascimento() != null ? atleta.getDataNascimento().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate() : null)
            .email(atleta.getContato().getEmail()!=null ? atleta.getContato().getEmail() : null)
            .telefone(atleta.getContato().getTelefone()!=null ? atleta.getContato().getTelefone() : null)
            .tipo(TipoPessoa.ATLETA)
            .ativo(true)
            .build();
        return pessoaRepository.save(pessoa);
    }

    /**
     * Cria ou obtém uma pessoa
     */
    @Transactional
    public Pessoa criarOuObterPessoa(String nome, String cpf, String email, String telefone, String dtNascimento) {
        String cpfLimpo=cpf.replaceAll("[^\\d]", ""); 
        return pessoaRepository.findByCpf(cpfLimpo)
            .orElseGet(() -> pessoaRepository.save(
                Pessoa.builder()
                .nome(nome)
                .cpf(cpfLimpo)
                .email(email)
                .telefone(telefone)
                .dataNascimento(dtNascimento != null ? java.time.LocalDate.parse(dtNascimento) : null)
                .ativo(true)
                .tipo(TipoPessoa.INTEGRANTE)
                .build()
            ));
    }

    @Transactional
    public void atualizarPessoa(Pessoa pessoa) {
        pessoaRepository.save(pessoa);
    }

    /**
     * Mapeia Pessoa para PessoaDto
     */
    private PessoaDto mapearPessoaParaDto(Pessoa pessoa) {
        if (pessoa == null) {
            return null;
        }

        return PessoaDto.builder()
            .id(pessoa.getId())
            .nome(pessoa.getNome())
            .cpf(pessoa.getCpf())
            .dataNascimento(pessoa.getDataNascimento())
            .email(pessoa.getEmail())
            .telefone(pessoa.getTelefone())
            .tipo(pessoa.getTipo())
            .ativo(pessoa.getAtivo())
            .build();
    }
}
