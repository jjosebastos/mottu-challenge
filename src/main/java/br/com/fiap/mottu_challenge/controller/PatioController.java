package br.com.fiap.mottu_challenge.controller;

import br.com.fiap.mottu_challenge.dto.request.PatioRequest;
import br.com.fiap.mottu_challenge.dto.response.FilialResponse;
import br.com.fiap.mottu_challenge.dto.response.PatioResponse;
import br.com.fiap.mottu_challenge.model.Patio;
import br.com.fiap.mottu_challenge.service.PatioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patio")
public class PatioController {

    @Autowired
    private PatioService patioService;


    @PostMapping
    @CacheEvict("patios")
    @Operation(summary = "Criar pátios.", description = "Criação de pátios.",
    responses = {
            @ApiResponse(responseCode = "201", description = "Criação de patio feita com sucesso."),
            @ApiResponse(responseCode = "400", description = "Campos inválidos."),
            @ApiResponse(responseCode = "404", description = "Filial não encontrada.")
    })
    public ResponseEntity<PatioResponse> create(@RequestBody PatioRequest patio){
        var created = patioService.create(patio);
        return ResponseEntity.status(201).body(created);
    }


    @PutMapping("/{id}")
    @CacheEvict("patios")
    @Operation(summary = "Atualizar pátios.", description = "Atualização de pátios.", responses = {
            @ApiResponse(responseCode = "200", description = "Atualiazdo com sucesso."),
            @ApiResponse(responseCode = "400", description = "Requisição inválida."),
            @ApiResponse(responseCode = "404", description = "Registro não encontrado.")
    })
    public ResponseEntity<PatioResponse> update(@PathVariable UUID id, @RequestBody PatioRequest request){
        var updated = patioService.update(id, request);
        return ResponseEntity.status(200).body(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletar pátios.", description = "Atualização da flag de pátios (false).", responses = {
            @ApiResponse(responseCode = "201", description = "Removido com sucesso."),
            @ApiResponse(responseCode = "400", description = "Requisição inválida."),
            @ApiResponse(responseCode = "404", description = "Registro não encontrado.")
    })
    public void delete(@PathVariable UUID id){
        patioService.delete(id);
    }

    @GetMapping("/{id}")
    @Cacheable("patios")
    @Operation(summary = "Buscar pátios.", description = "Busca de pátios por id que contenham a flag ativo.", responses = {
            @ApiResponse(responseCode = "201", description = "Encontrado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Requisição inválida."),
            @ApiResponse(responseCode = "404", description = "Registro não encontrado.")
    })
    public ResponseEntity<Patio> get(@PathVariable UUID id){
        var found = patioService.findById(id);
        return ResponseEntity.status(200).body(found);
    }

    @GetMapping("/all")
    @Operation(summary = "Buscar Patios", description = "Fazer a busca de todas os patios",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Encontrado com sucesso."),
                    @ApiResponse(responseCode = "404", description = "Nenhum registro encontrado.")
            }
    )
    public ResponseEntity<List<PatioResponse>> getAll() {
        var found = this.patioService.findAll();
        return ResponseEntity.ok(found);
    }

}

