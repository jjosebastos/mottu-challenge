package br.com.fiap.mottu_challenge.controller;


import br.com.fiap.mottu_challenge.dto.request.PatioEventoRequest;
import br.com.fiap.mottu_challenge.dto.response.PatioEventoResponse;
import br.com.fiap.mottu_challenge.dto.response.PatioResponse;
import br.com.fiap.mottu_challenge.model.PatioEvento;
import br.com.fiap.mottu_challenge.service.PatioEventoService;
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
@RequestMapping("/patioevento")
public class PatioEventoController {

    @Autowired
    private PatioEventoService patioEventoService;

    @PostMapping
    @CacheEvict("patioeventos")
    @Operation(summary = "Cria PatioEventos", description = "Cria PatioEventos de filiais", responses = {
            @ApiResponse(responseCode = "201", description = "Criado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Requisição inválida."),
            @ApiResponse(responseCode = "404", description = "Registro não encontrado.")
    })
    public ResponseEntity<PatioEventoResponse> create(@Valid @RequestBody PatioEventoRequest request){
        var saved = this.patioEventoService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/{id}")
    @Cacheable("patioeventos")
    @Operation(summary = "Busca Geofences", description = "Busca PatioEventos com base no ID fornecido", responses = {
            @ApiResponse(responseCode = "200", description = "Encontrado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Requisição inválida."),
            @ApiResponse(responseCode = "404", description = "Registro não encontrado.")
    })
    public ResponseEntity<PatioEventoResponse> getById(@PathVariable UUID id){
        var found = this.patioEventoService.findById(id);
        return ResponseEntity.ok(found);
    }

    @GetMapping("/all")
    @Operation(summary = "Buscar PatioEventos", description = "Fazer a busca de todos os PatioEventos",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Encontrado com sucesso."),
                    @ApiResponse(responseCode = "404", description = "Nenhum registro encontrado.")
            }
    )
    public ResponseEntity<List<PatioEventoResponse>> getAll() {
        var found = this.patioEventoService.findAll();
        return ResponseEntity.ok(found);
    }
}
