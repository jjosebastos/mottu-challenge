package br.com.fiap.mottu_challenge.controller;

import br.com.fiap.mottu_challenge.dto.request.ManutencaoRequest;
import br.com.fiap.mottu_challenge.dto.response.ManutencaoResponse;
import br.com.fiap.mottu_challenge.model.Manutencao;
import br.com.fiap.mottu_challenge.service.ManutencaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/manutencao")
public class ManutencaoController {

    @Autowired
    private ManutencaoService manutencaoService;

    @PostMapping
    @CacheEvict(value = "manutencoes", allEntries = true)
    @Operation(summary = "Cadastrar Manutenção", description = "Insere uma Manutençao.", responses = {
            @ApiResponse(responseCode = "201", description = "Criada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Registro não encontrado."),
    })
    public ResponseEntity<ManutencaoResponse> create(@Valid @RequestBody ManutencaoRequest request) {
        var saved = this.manutencaoService.save(request);
        return ResponseEntity.status(201).body(saved);
    }

    @PutMapping("/{id}")
    @CacheEvict(value = "manutencoes")
    @Operation(summary = "Atualizar Manutenção", description = "Atualiza registros de manutenção com base no ID fornecido.", responses = {
            @ApiResponse(responseCode = "200", description = "Atualizado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Requisição inválida."),
            @ApiResponse(responseCode = "404", description = "Registro não encontrado.")
    })
    public ResponseEntity<ManutencaoResponse> update(@PathVariable UUID id,
                                                     @Valid @RequestBody ManutencaoRequest request) {
        var saved = this.manutencaoService.update(id, request);
        return ResponseEntity.status(200).body(saved);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remover manutenções", description = "Atualiza a flag de manutenção com base no ID fornecido.", responses = {
            @ApiResponse(responseCode = "204", description = "Removido com sucesso."),
            @ApiResponse(responseCode = "400", description = "Requisição inválida."),
            @ApiResponse(responseCode = "404", description = "Registro não encontrado.")
    })
    public void delete(@PathVariable UUID id) {
        this.manutencaoService.delete(id);
    }

    @GetMapping("/{id}")
    @Cacheable("manutencao")
    @Operation(summary = "Buscar Manutenções", description = "Busca registros de Manutenção com base no ID fornecido.",  responses = {
            @ApiResponse(responseCode = "200", description = "Encontrado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Requisição inválida."),
            @ApiResponse(responseCode = "404", description = "Registro não encontrado.")
    })
    public ResponseEntity<Manutencao> get(@PathVariable UUID id) {
        var found = this.manutencaoService.findById(id);
        return ResponseEntity.status(200).body(found);
    }

    @GetMapping("/all")
    @Operation(summary = "Buscar manutenções", description = "Fazer a busca de todas as manutenções",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Encontrado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Registros não encontrados.")
            }
    )
    public ResponseEntity<List<ManutencaoResponse>> getAll(){
        var manutencoes = this.manutencaoService.findAll();
        return ResponseEntity.ok(manutencoes);
    }
}

