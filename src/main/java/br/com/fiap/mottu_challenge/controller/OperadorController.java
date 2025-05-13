package br.com.fiap.mottu_challenge.controller;

import br.com.fiap.mottu_challenge.dto.request.OperadorRequest;
import br.com.fiap.mottu_challenge.dto.request.OperadorRequestList;
import br.com.fiap.mottu_challenge.dto.response.OperadorResponse;
import br.com.fiap.mottu_challenge.model.Operador;
import br.com.fiap.mottu_challenge.service.OperadorService;
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
@RequestMapping("/operador")
public class OperadorController {

    @Autowired
    private OperadorService operadorService;

    @PostMapping
    @CacheEvict(value = "operadores", allEntries = true)
    @Operation(summary = "Cria Operadores", description = "Cria Operadores das motos",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Operadores criados com sucesso."),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida.")
            })
    public ResponseEntity<List<OperadorResponse>> create(@Valid  @RequestBody OperadorRequestList operadorList) {
        var saved = this.operadorService.create(operadorList.getOperadorList());
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza Operadores", description = "Atualiza o Operador com base no ID fornecido",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Operador atualizado."),
                    @ApiResponse(responseCode = "400", description = "ID fornecido inválido."),
                    @ApiResponse(responseCode = "404", description = "Operador não encontrado.")
            })
    public ResponseEntity<OperadorResponse> update(@PathVariable UUID id, @RequestBody OperadorRequest operadorRequest) {
        var updated = this.operadorService.updateById(id, operadorRequest);
        return ResponseEntity.ok(updated.getBody());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca Operadores", description = "Busca Operadores com base no ID fornecido",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Encontrado com sucesso."),
                    @ApiResponse(responseCode = "400", description = "ID fornecido inválido."),
                    @ApiResponse(responseCode = "404", description = "Operador não encontrado.")
            })
    @Cacheable("operadores")
    public ResponseEntity<Operador> getById(@PathVariable UUID id) {
        var found = this.operadorService.getById(id);
        return ResponseEntity.ok(found);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remove Operadores", description = "Remove o Operador com base no ID fornecido.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Operador removido com sucesso."),
                    @ApiResponse(responseCode = "400", description = "ID fornecido invalido."),
                    @ApiResponse(responseCode = "404", description = "Operador não encontrada.")
            })
    public void delete(@PathVariable UUID id) {
        this.operadorService.deleteById(id);
    }


}
