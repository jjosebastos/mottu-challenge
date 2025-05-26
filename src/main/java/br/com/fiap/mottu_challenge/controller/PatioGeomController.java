package br.com.fiap.mottu_challenge.controller;

import br.com.fiap.mottu_challenge.dto.request.PatioGeomRequest;
import br.com.fiap.mottu_challenge.dto.response.PatioEventoResponse;
import br.com.fiap.mottu_challenge.dto.response.PatioGeomResponse;
import br.com.fiap.mottu_challenge.model.PatioGeom;
import br.com.fiap.mottu_challenge.service.PatioGeomService;
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
@RequestMapping("/patiogeom")
public class PatioGeomController {

    @Autowired
    private PatioGeomService patioGeomService;

    @PostMapping
    @CacheEvict("patiogeom")
    @Operation(summary = "Cadastrar PatioGeom", description = "Insere um registro PatioGeom.", responses = {
            @ApiResponse(responseCode = "201", description = "Criado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Registro não encontrado."),
    })
    public ResponseEntity<PatioGeomResponse> create(@Valid @RequestBody PatioGeomRequest patioGeom) {
        var saved = this.patioGeomService.save(patioGeom);
        return ResponseEntity.status(201).body(saved);
    }

    @PutMapping("/{id}")
    @CacheEvict("patiogeom")
    @Operation(summary = "Atualizar PatioGeom", description = "Atualiza registros de PatioGeom com base no ID fornecido.", responses = {
            @ApiResponse(responseCode = "200", description = "Atualizado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Requisição inválida."),
            @ApiResponse(responseCode = "404", description = "Registro não encontrado.")
    })
    public ResponseEntity<PatioGeomResponse> put(@PathVariable UUID id,
                                                 @Valid @RequestBody PatioGeomRequest patioGeom) {
        var updated = this.patioGeomService.update(id, patioGeom);
        return ResponseEntity.status(200).body(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remover PatioGeom", description = "Atualiza a flag de PatioGeom com base no ID fornecido.", responses = {
            @ApiResponse(responseCode = "204", description = "Removido com sucesso."),
            @ApiResponse(responseCode = "400", description = "Requisição inválida."),
            @ApiResponse(responseCode = "404", description = "Registro não encontrado.")
    })
    public void delete(@PathVariable UUID id) {
        this.patioGeomService.delete(id);
    }

    @GetMapping("/{id}")
    @Cacheable("patiogeom")
    @Operation(summary = "Buscar PatioGeom", description = "Busca registros de PatioGeom com base no ID fornecido.",  responses = {
            @ApiResponse(responseCode = "200", description = "Encontrado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Requisição inválida."),
            @ApiResponse(responseCode = "404", description = "Registro não encontrado.")
    })
    public ResponseEntity<PatioGeom> getById(@PathVariable UUID id) {
        var saved = this.patioGeomService.findById(id);
        return ResponseEntity.status(200).body(saved);
    }

    @GetMapping("/all")
    @Operation(summary = "Buscar PatioGeom", description = "Fazer a busca de todos os PatioGeoms",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Encontrado com sucesso."),
                    @ApiResponse(responseCode = "404", description = "Nenhum registro encontrado.")
            }
    )
    public ResponseEntity<List<PatioGeomResponse>> getAll() {
        var found = this.patioGeomService.findAll();
        return ResponseEntity.ok(found);
    }

}
