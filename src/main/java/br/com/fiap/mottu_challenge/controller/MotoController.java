package br.com.fiap.mottu_challenge.controller;

import br.com.fiap.mottu_challenge.dto.request.MotoRequest;
import br.com.fiap.mottu_challenge.dto.response.MotoResponse;
import br.com.fiap.mottu_challenge.model.Moto;
import br.com.fiap.mottu_challenge.model.enums.Modelo;
import br.com.fiap.mottu_challenge.service.MotoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/moto")
public class MotoController {

    @Autowired
    private MotoService motoService;

    @CacheEvict(value = "motos")
    @Operation(summary = "Criar motos", description = "Insere novos registros de moto.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Criado com sucesso."),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida."),
                    @ApiResponse(responseCode = "404", description = "ID Filial/Operador inválido.")
            })
    @PostMapping
    public ResponseEntity<MotoResponse> create(@RequestBody MotoRequest moto) {
        var saved = this.motoService.save(moto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @CacheEvict(value = "motos")
    @Operation(summary = "Atualizar motos", description = "Atualiza registros de moto com base no ID fornecido.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Atualizado com sucesso."),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida."),
                    @ApiResponse(responseCode = "404", description = "Registro não encontrado/ID Operador ou Filial inválido.")
            })
    @PutMapping("/{id}")
    public ResponseEntity<MotoResponse> update(@PathVariable UUID id, @RequestBody MotoRequest moto) {
        var updated = this.motoService.update(id, moto);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Remover motos", description = "Remove registros de moto com base no ID fornecido.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Removido com sucesso."),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida."),
                    @ApiResponse(responseCode = "404", description = "Registro não encontrado.")
            })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable UUID id) {
        this.motoService.delete(id);
    }

    @Operation(summary = "Busca motos", description = "Busca registros de moto com base no ID fornecido.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Encontrado com sucesso."),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida."),
                    @ApiResponse(responseCode = "404", description = "Registro não encontrado.")
            })
    @Cacheable("motos")
    @GetMapping("/{id}")
    public ResponseEntity<Moto> findById(@PathVariable UUID id) {
        var foundId = this.motoService.getById(id);
        if (foundId == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(foundId);
    }

    @Operation(summary = "Busca motos", description = "Busca registros de moto com base no Modelo fornecido.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Encontrado com sucesso."),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida."),
                    @ApiResponse(responseCode = "404", description = "Registro não encontrado.")
            })
    @Cacheable("motos")
    @GetMapping("/modelo/{modelo}")
    public ResponseEntity<List<Moto>> findByModelo(@PathVariable Modelo modelo) {
        var foundModelo = this.motoService.getByModelo(modelo);
        return ResponseEntity.ok(foundModelo);
    }

}

