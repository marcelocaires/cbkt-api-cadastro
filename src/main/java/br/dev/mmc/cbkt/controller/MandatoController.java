package br.dev.mmc.cbkt.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.dev.mmc.cbkt.controller.forms.MandatoCargoRequest;
import br.dev.mmc.cbkt.controller.forms.MandatoComCargosRequest;
import br.dev.mmc.cbkt.controller.forms.MandatoRequest;
import br.dev.mmc.cbkt.controller.forms.MandatoUpdateRequest;
import br.dev.mmc.cbkt.controller.responses.MandatoCargoDto;
import br.dev.mmc.cbkt.controller.responses.MandatoResponseDto;
import br.dev.mmc.cbkt.controller.responses.ParametroResponse;
import br.dev.mmc.cbkt.domain.Cargo;
import br.dev.mmc.cbkt.domain.enums.MotivoSaida;
import br.dev.mmc.cbkt.domain.enums.TipoEntidade;
import br.dev.mmc.cbkt.domain.enums.TipoOcupacao;
import br.dev.mmc.cbkt.domain.enums.TipoPessoa;
import br.dev.mmc.cbkt.domain.enums.TipoVinculo;
import br.dev.mmc.cbkt.repository.MandatoRepository;
import br.dev.mmc.cbkt.service.MandatoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/mandato")
@Validated
@RequiredArgsConstructor
public class MandatoController {

    private final MandatoService mandatoService;
    private final MandatoRepository mandatoRepository;

    @PostMapping
    @Operation(summary = "Cria um novo mandato")
    public ResponseEntity<MandatoResponseDto> criar(@Valid @RequestBody MandatoRequest request) {
        mandatoService.criarMandato(
            request.getTipoEntidade(),
            request.getEntidadeId(),
            request.getDataInicio(),
            request.getDataFim(),
            request.getDescricao()
        );
        MandatoResponseDto mandatoDto = mandatoService.obterMandatoAtivo(
            request.getTipoEntidade(),
            request.getEntidadeId()
        );
        return ResponseEntity.ok(mandatoDto);
    }

    @PostMapping("/com-cargos")
    @Operation(summary = "Cria um novo mandato com cargos associados")
    public ResponseEntity<MandatoResponseDto> criarComCargos(@Valid @RequestBody MandatoComCargosRequest request) {
        MandatoResponseDto mandatoDto = mandatoService.criarMandatoComCargos(request); 
        return ResponseEntity.status(HttpStatus.CREATED).body(mandatoDto);
    }

    @GetMapping
    @Operation(summary = "Lista mandatos por entidade")
    public ResponseEntity<List<MandatoResponseDto>> listar(
        @RequestParam TipoEntidade tipoEntidade,
        @RequestParam Long entidadeId
    ) {
        List<MandatoResponseDto> resposta = mandatoService
            .listarMandatosEntidade(tipoEntidade, entidadeId);
        return ResponseEntity.ok(resposta);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca mandato por id")
    public ResponseEntity<MandatoResponseDto> buscar(@PathVariable Long id) {
        MandatoResponseDto mandatoDto = mandatoRepository.findById(id)
            .map(mandatoService::convertToDto)
            .orElseThrow(() -> new RuntimeException("Mandato não encontrado"));
        return ResponseEntity.ok(mandatoDto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um mandato existente")
    public ResponseEntity<MandatoResponseDto> atualizar(@PathVariable Long id, 
                                                         @Valid @RequestBody MandatoUpdateRequest request) {
        MandatoResponseDto mandatoDto = mandatoService.atualizarMandato(
            id,
            request.getDataInicio(),
            request.getDataFim(),
            request.getDescricao(),
            request.getAtivo()
        );
        return ResponseEntity.ok(mandatoDto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um mandato")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        mandatoService.deletarMandato(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/desativar")
    @Operation(summary = "Desativa um mandato")
    public ResponseEntity<MandatoResponseDto> desativar(@PathVariable Long id) {
        MandatoResponseDto mandatoDto = mandatoService.desativarMandato(id);
        return ResponseEntity.ok(mandatoDto);
    }

    @PutMapping("/{id}/ativar")
    @Operation(summary = "Ativa um mandato desativado")
    public ResponseEntity<MandatoResponseDto> ativar(@PathVariable Long id) {
        MandatoResponseDto mandatoDto = mandatoService.ativarMandato(id);
        return ResponseEntity.ok(mandatoDto);
    }

    @GetMapping("/cargos")
    @Operation(
        summary = "Lista todos os cargos disponíveis para ocupação de mandatos",
        description = "Retorna uma lista com todos os cargos disponíveis para ocupação de mandatos no sistema"
    )
    public ResponseEntity<List<Cargo>> getCargos() {
        return ResponseEntity.ok(mandatoService.obterCargosAtivos());
    }

    @GetMapping("/cargo/tipo-vinculo")
    public ResponseEntity<List<ParametroResponse>> getTipoVinculo() {
        List<ParametroResponse> parametros = java.util.Arrays.stream(TipoVinculo.values())
            .map(tipo -> new ParametroResponse(tipo.nome(), tipo.getDescricao()))
            .collect(java.util.stream.Collectors.toList());
        return ResponseEntity.ok(parametros);
    }

    @GetMapping("/cargo/tipo-ocupacao")
    public ResponseEntity<List<ParametroResponse>> getTipoOcupacao() {
        List<ParametroResponse> parametros = java.util.Arrays.stream(TipoOcupacao.values())
            .map(tipo -> new ParametroResponse(tipo.name(), tipo.getDescricao()))
            .collect(java.util.stream.Collectors.toList());
        
        return ResponseEntity.ok(parametros);
    }

    @GetMapping("/cargo/motivo-saida")
    public ResponseEntity<List<ParametroResponse>> getMotivoSaida() {
        List<ParametroResponse> parametros = java.util.Arrays.stream(MotivoSaida.values())
            .map(motivo -> new ParametroResponse(motivo.name(), motivo.getDescricao()))
            .collect(java.util.stream.Collectors.toList());
        
        return ResponseEntity.ok(parametros);
    }

    @GetMapping("/cargo/tipo-pessoa")
    public ResponseEntity<List<ParametroResponse>> getTipoPessoa() {
        List<ParametroResponse> parametros = java.util.Arrays.stream(TipoPessoa.values())
            .map(tipo -> new ParametroResponse(tipo.name(), tipo.getDescricao()))
            .collect(java.util.stream.Collectors.toList());
        
        return ResponseEntity.ok(parametros);
    }

    @GetMapping("/cargo/tipo-entidade")
    public ResponseEntity<List<ParametroResponse>> getTipoEntidade() {
        List<ParametroResponse> parametros = java.util.Arrays.stream(TipoEntidade.values())
            .map(tipo -> new ParametroResponse(tipo.name(), tipo.getDescricao()))
            .collect(java.util.stream.Collectors.toList());
        
        return ResponseEntity.ok(parametros);
    }

    // ==================== Operações com cargos de mandatos ====================

    @GetMapping("/{mandatoId}/cargos")
    @Operation(summary = "Lista cargos de um mandato")
    public ResponseEntity<List<MandatoCargoDto>> listarCargosMandato(@PathVariable Long mandatoId) {
        List<MandatoCargoDto> cargos = mandatoService.obterCargosMandato(mandatoId);
        return ResponseEntity.ok(cargos);
    }

    @PostMapping("/{mandatoId}/cargo")
    @Operation(summary = "Adiciona um cargo a um mandato")
    public ResponseEntity<MandatoCargoDto> adicionarCargoMandato(
        @PathVariable Long mandatoId,
        @Valid @RequestBody MandatoCargoRequest request) {
        MandatoCargoDto cargoDto = mandatoService.adicionarCargoMandato(request,mandatoId);
        return ResponseEntity.status(HttpStatus.CREATED).body(cargoDto);
    }

    @PutMapping("/{mandatoId}/cargos/{cargoId}")
    @Operation(summary = "Atualiza um cargo de um mandato")
    public ResponseEntity<MandatoCargoDto> atualizarCargoMandato(@PathVariable Long mandatoId,
                                                                  @PathVariable Long cargoId,
                                                                  @Valid @RequestBody MandatoCargoRequest request) {
        MandatoCargoDto cargoDto = mandatoService.atualizarCargoMandato(
            mandatoId,
            cargoId,
            request.getTipoVinculo(),
            request.getDataInicio(),
            request.getDataFim(),
            request.getAtivo(),
            request.getMotivoSaida(),
            request.getObservacao()
        );
        return ResponseEntity.ok(cargoDto);
    }

    @DeleteMapping("/{mandatoId}/cargos/{cargoId}")
    @Operation(summary = "Remove um cargo de um mandato")
    public ResponseEntity<Void> removerCargoMandato(@PathVariable Long mandatoId,
                                                     @PathVariable Long cargoId) {
        mandatoService.removerCargoMandato(mandatoId, cargoId);
        return ResponseEntity.noContent().build();
    }
}