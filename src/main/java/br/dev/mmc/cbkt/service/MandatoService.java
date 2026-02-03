package br.dev.mmc.cbkt.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.dev.mmc.cbkt.controller.forms.MandatoCargoRequest;
import br.dev.mmc.cbkt.controller.forms.MandatoComCargosRequest;
import br.dev.mmc.cbkt.controller.responses.MandatoCargoDto;
import br.dev.mmc.cbkt.controller.responses.MandatoResponseDto;
import br.dev.mmc.cbkt.controller.responses.PessoaDto;
import br.dev.mmc.cbkt.domain.Cargo;
import br.dev.mmc.cbkt.domain.Mandato;
import br.dev.mmc.cbkt.domain.MandatoCargo;
import br.dev.mmc.cbkt.domain.Pessoa;
import br.dev.mmc.cbkt.domain.enums.MotivoSaida;
import br.dev.mmc.cbkt.domain.enums.TipoEntidade;
import br.dev.mmc.cbkt.domain.enums.TipoVinculo;
import br.dev.mmc.cbkt.repository.CargoRepository;
import br.dev.mmc.cbkt.repository.MandatoRepository;
import br.dev.mmc.cbkt.repository.PessoaRepository;
import br.dev.mmc.cbkt.util.JodaTimeUtil;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MandatoService {

    private final MandatoRepository mandatoRepository;
    private final CargoRepository cargoRepository;
    private final PessoaRepository pessoaRepository;
    private final PessoaService pessoaService;

    /**
     * Cria um novo mandato para uma entidade (Clube/Federação/Confederação)
     */
    @Transactional
    public Mandato criarMandato(TipoEntidade tipoEntidade, Long entidadeId, LocalDate dataInicio, 
                                LocalDate dataFim, String descricao) {
        
        Mandato mandato = Mandato.builder()
            .tipoEntidade(tipoEntidade)
            .entidadeId(entidadeId)
            .dataInicio(dataInicio)
            .dataFim(dataFim)
            .descricao(descricao)
            .ativo(true)
            .build();
        
        return mandatoRepository.save(mandato);
    }

    /**
     * Cria um novo mandato com cargos associados em uma única operação
     */
    @Transactional
    public MandatoResponseDto criarMandatoComCargos(MandatoComCargosRequest request) {

        // Criar o mandato
        Mandato mandato = Mandato.builder()
            .tipoEntidade(request.getTipoEntidade())
            .entidadeId(request.getEntidadeId())
            .dataInicio(request.getDataInicio())
            .dataFim(request.getDataFim())
            .descricao(request.getDescricao())
            .ativo(true)
            .build();
        
        Mandato mandatoSalvo = mandatoRepository.save(mandato);

        // Adicionar cargos ao mandato
        if (request.getCargos() != null && !request.getCargos().isEmpty()) {
            for (MandatoCargoRequest cargoRequest : request.getCargos()) {
                adicionarCargoMandato(cargoRequest, mandatoSalvo.getId());  
            }
        }

        return mapearParaDto(mandatoRepository.findById(mandatoSalvo.getId()).get());
    }

    /**
     * Busca o mandato ativo de uma entidade
     */
    public MandatoResponseDto obterMandatoAtivo(TipoEntidade tipoEntidade, Long entidadeId) {
        return mandatoRepository.findByTipoEntidadeAndEntidadeIdAndAtivo(tipoEntidade, entidadeId, true)
            .map(this::mapearParaDto)
            .orElse(null);
    }

    /**
     * Lista todos os mandatos de uma entidade, ordenados por data de início (descendente)
     */
    public List<MandatoResponseDto> listarMandatosEntidade(TipoEntidade tipoEntidade, Long entidadeId) {
        return mandatoRepository.findByTipoEntidadeAndEntidadeIdOrderByDataInicioDesc(tipoEntidade, entidadeId)
            .stream()
            .map(this::mapearParaDto)
            .collect(Collectors.toList());
    }

    /**
     * Verifica se um mandato foi alterado após criação
     */
    public boolean foiAlterado(Mandato mandato) {
        return mandato.foiAlterado();
    }

    /**
     * Obtém pessoa por CPF
     */
    public Pessoa obterPessoaPorCpf(String cpf) {
        return pessoaRepository.findByCpf(cpf).orElse(null);
    }

    /**
     * Cria ou obtém uma pessoa
     */
    @Transactional
    public Pessoa criarOuObterPessoa(String nome, String cpf, String email, String telefone) {
        return pessoaRepository.findByCpf(cpf)
            .orElseGet(() -> pessoaRepository.save(Pessoa.builder()
                .nome(nome)
                .cpf(cpf)
                .email(email)
                .telefone(telefone)
                .ativo(true)
                .build()));
    }

    /**
     * Obtém cargo por nome
     */
    public Cargo obterCargoPorNome(String nome) {
        return cargoRepository.getByNomeAndAtivo(nome,true).orElse(null);
    }

    public List<Cargo> obterCargosAtivos() {
        return cargoRepository.findByAtivo(true);
    }

    /**
     * Converte um Mandato em MandatoResponseDto (método público para uso em controllers)
     */
    public MandatoResponseDto convertToDto(Mandato mandato) {
        return mapearParaDto(mandato);
    }

    /**
     * Atualiza um mandato existente
     */
    @Transactional
    public MandatoResponseDto atualizarMandato(Long mandatoId, LocalDate dataInicio, LocalDate dataFim,
                                               String descricao, Boolean ativo) {
        Mandato mandato = mandatoRepository.findById(mandatoId)
            .orElseThrow(() -> new RuntimeException("Mandato não encontrado com ID: " + mandatoId));

        mandato.setDataInicio(dataInicio);
        mandato.setDataFim(dataFim);
        mandato.setDescricao(descricao);
        mandato.setAtivo(ativo);

        Mandato mandatoAtualizado = mandatoRepository.save(mandato);
        return mapearParaDto(mandatoAtualizado);
    }

    /**
     * Deleta um mandato e seus cargos associados
     */
    @Transactional
    public void deletarMandato(Long mandatoId) {
        if (!mandatoRepository.existsById(mandatoId)) {
            throw new RuntimeException("Mandato não encontrado com ID: " + mandatoId);
        }
        mandatoRepository.deleteById(mandatoId);
    }

    /**
     * Desativa um mandato (soft delete)
     */
    @Transactional
    public MandatoResponseDto desativarMandato(Long mandatoId) {
        Mandato mandato = mandatoRepository.findById(mandatoId)
            .orElseThrow(() -> new RuntimeException("Mandato não encontrado com ID: " + mandatoId));

        mandato.setAtivo(false);
        Mandato mandatoAtualizado = mandatoRepository.save(mandato);
        return mapearParaDto(mandatoAtualizado);
    }

    /**
     * Ativa um mandato
     */
    @Transactional
    public MandatoResponseDto ativarMandato(Long mandatoId) {
        Mandato mandato = mandatoRepository.findById(mandatoId)
            .orElseThrow(() -> new RuntimeException("Mandato não encontrado com ID: " + mandatoId));

        mandato.setAtivo(true);
        Mandato mandatoAtualizado = mandatoRepository.save(mandato);
        return mapearParaDto(mandatoAtualizado);
    }

    @Transactional
    public MandatoCargoDto adicionarCargoMandato(MandatoCargoRequest request, Long mandatoId) {
        
        Mandato mandato = mandatoRepository.findById(mandatoId)
            .orElseThrow(() -> new RuntimeException("Mandato não encontrado com ID: " + mandatoId));

        if(request.getPessoa()==null){
            throw new RuntimeException("Pessoa é obrigatória para adicionar cargo ao mandato");
        }
        Pessoa pessoa=null;
        if(request.getPessoa().getId()==null){
            pessoa = pessoaService.criarOuObterPessoa(
                request.getPessoa().getNome(),
                request.getPessoa().getCpf(),
                request.getPessoa().getEmail(),
                request.getPessoa().getTelefone(),
                request.getPessoa().getDataNascimento()
            );
        }else{
            pessoa = pessoaRepository.findById(request.getPessoa().getId()).orElseThrow(
                ()->new RuntimeException("Pessoa não encontrada com ID: "+request.getPessoa().getId())
            );
            pessoa.setNome(request.getPessoa().getNome());
            pessoa.setEmail(request.getPessoa().getEmail());
            pessoa.setTelefone(request.getPessoa().getTelefone());
            pessoa.setDataNascimento(JodaTimeUtil.parseStringDateBRtoLavaLocalDate(request.getPessoa().getDataNascimento()));
            pessoaService.atualizarPessoa(pessoa);
        }

        Cargo cargo = cargoRepository.findById(request.getCargo().getId())
            .orElseThrow(() -> new RuntimeException("Cargo não encontrado com ID: " + request.getCargo().getId()));
        MandatoCargo substituiu = null;
        if (request.getSubstituiuId() != null) {
            // Buscar a ocupação anterior se existir
            mandato.getCargos().stream()
                .filter(c -> c.getId().equals(request.getSubstituiuId()))
                .findFirst()
                .ifPresent(c -> {
                    // Aqui você pode fazer lógica de encerramento da ocupação anterior se necessário
                });
        }

        MandatoCargo mandatoCargo = MandatoCargo.builder()
            .mandato(mandato)
            .pessoa(pessoa)
            .cargo(cargo)
            .tipoVinculo(request.getTipoVinculo())
            .dataInicio(request.getDataInicio())
            .dataFim(request.getDataFim())
            .ativo(true)
            .substituiu(substituiu)
            .motivoSaida(request.getMotivoSaida())
            .observacao(request.getObservacao())
            .build();

        if(mandato.getCargos()==null){
            mandato.setCargos(new ArrayList<MandatoCargo>());
        }
        
        mandato.getCargos().add(mandatoCargo);
        mandatoRepository.save(mandato);

        return mapearMandatoCargoParaDto(mandatoCargo);
    }

    /**
     * Atualiza um cargo de um mandato
     */
    @Transactional
    public MandatoCargoDto atualizarCargoMandato(Long mandatoId, Long cargoId,
        TipoVinculo tipoVinculo, LocalDate dataInicio, LocalDate dataFim,
        Boolean ativo, MotivoSaida motivoSaida, String observacao) {
        
        Mandato mandato = mandatoRepository.findById(mandatoId)
            .orElseThrow(() -> new RuntimeException("Mandato não encontrado com ID: " + mandatoId));

        MandatoCargo mandatoCargo = mandato.getCargos().stream()
            .filter(c -> c.getId().equals(cargoId))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Cargo não encontrado no mandato com ID: " + cargoId));

        mandatoCargo.setTipoVinculo(tipoVinculo);
        mandatoCargo.setDataInicio(dataInicio);
        mandatoCargo.setDataFim(dataFim);
        mandatoCargo.setAtivo(ativo);
        mandatoCargo.setMotivoSaida(motivoSaida);
        mandatoCargo.setObservacao(observacao);

        mandatoRepository.save(mandato);

        return mapearMandatoCargoParaDto(mandatoCargo);
    }

    /**
     * Remove um cargo de um mandato
     */
    @Transactional
    public void removerCargoMandato(Long mandatoId, Long cargoId) {
        Mandato mandato = mandatoRepository.findById(mandatoId)
            .orElseThrow(() -> new RuntimeException("Mandato não encontrado com ID: " + mandatoId));

        MandatoCargo mandatoCargo = mandato.getCargos().stream()
            .filter(c -> c.getId().equals(cargoId))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Cargo não encontrado no mandato com ID: " + cargoId));

        mandato.getCargos().remove(mandatoCargo);
        mandatoRepository.save(mandato);
    }

    /**
     * Obtém lista de cargos de um mandato
     */
    public List<MandatoCargoDto> obterCargosMandato(Long mandatoId) {
        Mandato mandato = mandatoRepository.findById(mandatoId)
            .orElseThrow(() -> new RuntimeException("Mandato não encontrado com ID: " + mandatoId));

        return mandato.getCargos().stream()
            .map(this::mapearMandatoCargoParaDto)
            .collect(Collectors.toList());
    }

    // Métodos privados de mapeamento
    
    /**
     * Mapeia Mandato para MandatoResponseDto
     */
    private MandatoResponseDto mapearParaDto(Mandato mandato) {
        List<MandatoCargoDto> cargosDto = mandato.getCargos() != null
            ? mandato.getCargos().stream()
                .map(this::mapearMandatoCargoParaDto)
                .collect(Collectors.toList())
            : List.of();

        return MandatoResponseDto.builder()
            .id(mandato.getId())
            .tipoEntidade(mandato.getTipoEntidade())
            .entidadeId(mandato.getEntidadeId())
            .dataInicio(mandato.getDataInicio())
            .dataFim(mandato.getDataFim())
            .ativo(mandato.getAtivo())
            .descricao(mandato.getDescricao())
            .criadoEm(mandato.getCriadoEm())
            .atualizadoEm(mandato.getAtualizadoEm())
            .criadoPorCpf(mandato.getCriadoPorCpf())
            .criadoPorNome(mandato.getCriadoPorNome())
            .atualizadoPorCpf(mandato.getAtualizadoPorCpf())
            .atualizadoPorNome(mandato.getAtualizadoPorNome())
            .cargos(cargosDto)
            .versao(mandato.getVersao())
            .build();
    }

    /**
     * Mapeia MandatoCargo para MandatoCargoDto
     */
    private MandatoCargoDto mapearMandatoCargoParaDto(MandatoCargo mandatoCargo) {
        PessoaDto pessoaDto = mapearPessoaParaDto(mandatoCargo.getPessoa());

        return MandatoCargoDto.builder()
            .id(mandatoCargo.getId())
            .pessoa(pessoaDto)
            .cargo(mandatoCargo.getCargo())
            .tipoVinculo(mandatoCargo.getTipoVinculo())
            .dataInicio(mandatoCargo.getDataInicio())
            .dataFim(mandatoCargo.getDataFim())
            .ativo(mandatoCargo.getAtivo())
            .substituiuId(mandatoCargo.getSubstituiu() != null ? mandatoCargo.getSubstituiu().getId() : null)
            .motivoSaida(mandatoCargo.getMotivoSaida())
            .observacao(mandatoCargo.getObservacao())
            .build();
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
