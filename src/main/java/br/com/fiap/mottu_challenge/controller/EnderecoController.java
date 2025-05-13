package br.com.fiap.mottu_challenge.controller;

import br.com.fiap.mottu_challenge.dto.request.EnderecoRequest;
import br.com.fiap.mottu_challenge.dto.request.EnderecoRequestList;
import br.com.fiap.mottu_challenge.dto.response.EnderecoResponse;
import br.com.fiap.mottu_challenge.model.Endereco;
import br.com.fiap.mottu_challenge.service.EnderecoService;
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
@RequestMapping("/endereco")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @PostMapping
    @CacheEvict(value = "enderecos", allEntries = true)
    @Operation(summary = "Cria endereços", description = "Cria endereços de filiais",
    responses = {
        @ApiResponse(responseCode = "201", description = "Endereço criado com sucesso."),
        @ApiResponse(responseCode = "400", description = "Requisição inválida."),
        @ApiResponse(responseCode = "404", description = "ID Filial inválido.")
    })
    public ResponseEntity<List<EnderecoResponse>> post(@Valid @RequestBody  EnderecoRequestList endereco) {
        var created = this.enderecoService.create(endereco);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza Endereços", description = "Atualiza o endereço com base no ID fornecido",
        responses = {
            @ApiResponse(responseCode = "200", description = "Endereço atualizado."),
            @ApiResponse(responseCode = "400", description = "ID fornecido inválido."),
            @ApiResponse(responseCode = "404", description = "Endereco não encontrado.")
    })
    public ResponseEntity<EnderecoResponse> put(@PathVariable UUID id,@RequestBody @Valid EnderecoRequest endereco) {
        var updated = this.enderecoService.update(id, endereco);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    @Cacheable("enderecos")
    @Operation(summary = "Busca Endereços", description = "Busca endereços com base no ID fornecido",
        responses = {
            @ApiResponse(responseCode = "200", description = "Encontrado com sucesso."),
            @ApiResponse(responseCode = "400", description = "ID fornecido inválido."),
            @ApiResponse(responseCode = "404", description = "Endereco não encontrado.")
    })
    public ResponseEntity<Endereco> get(@PathVariable UUID id) {
        var found = this.enderecoService.getById(id);
        return ResponseEntity.ok(found);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remove Endereços", description = "Remove o Endereço com base no ID fornecido.",
        responses = {
            @ApiResponse(responseCode = "204", description = "Endereco removido com sucesso."),
            @ApiResponse(responseCode = "400", description = "ID fornecido invalido."),
            @ApiResponse(responseCode = "404", description = "Endereco não encontrada.")
    })
    public void delete(@PathVariable UUID id) {
        this.enderecoService.delete(id);
    }




}
