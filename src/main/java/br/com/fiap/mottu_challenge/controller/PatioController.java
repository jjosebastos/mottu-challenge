package br.com.fiap.mottu_challenge.controller;

import br.com.fiap.mottu_challenge.dto.request.PatioRequest;
import br.com.fiap.mottu_challenge.dto.response.PatioResponse;
import br.com.fiap.mottu_challenge.model.Patio;
import br.com.fiap.mottu_challenge.service.PatioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/patio")
public class PatioController {

    @Autowired
    private PatioService patioService;


    @Operation(summary = "Criar pátios.", description = "Criação de pátios.",
    responses = {
            @ApiResponse(responseCode = "201", description = "Criação de patio feita com sucesso."),
            @ApiResponse(responseCode = "400", description = "Campos inválidos."),
            @ApiResponse(responseCode = "404", description = "Filial não encontrada.")
    })
    @PostMapping
    public ResponseEntity<PatioResponse> create(@RequestBody PatioRequest patio){
        var created = patioService.create(patio);
        return ResponseEntity.status(201).body(created);
    }

    @Operation(summary = "Atualizar pátios.", description = "Atualização de pátios.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Atualização de patio feita com sucesso."),
                    @ApiResponse(responseCode = "400", description = "Campos inválidos."),
                    @ApiResponse(responseCode = "404", description = "Filial/Patio não encontrados.")
            })
    @PutMapping("/{id}")
    public ResponseEntity<PatioResponse> update(@PathVariable UUID id, @RequestBody PatioRequest request){
        var updated = patioService.update(id, request);
        return ResponseEntity.status(200).body(updated);
    }

    @Operation(summary = "Deletar pátios.", description = "Atualização da flag de pátios (false).",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Exclusão de patio feita com sucesso."),
                    @ApiResponse(responseCode = "404", description = "Patio não encontrado.")
            })
    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id){
        patioService.delete(id);
    }

    @Operation(summary = "Buscar pátios.", description = "Busca de pátios por id que contenham a flag ativo.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Busca de patio feita com sucesso."),
                    @ApiResponse(responseCode = "400", description = "Busca inválida."),
                    @ApiResponse(responseCode = "404", description = "Patio não encontrado.")
            })
    @GetMapping("/{id}")
    public ResponseEntity<Patio> get(@PathVariable UUID id){
        var found = patioService.findById(id);
        return ResponseEntity.status(200).body(found);
    }

}

