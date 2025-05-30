package br.com.fiap.mottu_challenge.controller;
import br.com.fiap.mottu_challenge.dto.request.FilialRequest;
import br.com.fiap.mottu_challenge.dto.request.FilialRequestList;
import br.com.fiap.mottu_challenge.dto.response.FilialResponse;
import br.com.fiap.mottu_challenge.model.Filial;
import br.com.fiap.mottu_challenge.model.specification.FilialFilter;
import br.com.fiap.mottu_challenge.repository.FilialRepository;
import br.com.fiap.mottu_challenge.service.FilialService;
import br.com.fiap.mottu_challenge.specification.FilialSpecification;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/filial")
public class FilialController {


    @Autowired
    private FilialService filialService;

    @Autowired
    private FilialRepository filialRepository;

    @GetMapping
    @Operation(summary = "Busca Filial Specification", description = "Busca filial com base em filters",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Encontrado com sucesso."),
                    @ApiResponse(responseCode = "400", description = "ID fornecido inválido."),
                    @ApiResponse(responseCode = "404", description = "Filial não encontrada.")
            })
    public Page<Filial> index(
            FilialFilter filter,
            @PageableDefault(size = 10, sort = "date", direction = Sort.Direction.DESC) Pageable pageable){
        var specification = FilialSpecification.withFilters(filter);
        return filialRepository.findAll(specification, pageable);
    }

    @PostMapping
    @CacheEvict(value = "filiais", allEntries = true)
    @Operation(summary = "Cadastrar filial", description = "Insere uma filial.", responses = {
            @ApiResponse(responseCode = "201"),
            @ApiResponse(responseCode = "400"),
    })
    public ResponseEntity<List<FilialResponse>> create(@Valid @RequestBody FilialRequestList input) {
        var created = filialService.create(input);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(created);
    }

    @PutMapping("/{id}")
    @CacheEvict(value = "filiais", allEntries = true)
    @Operation(summary = "Atualizar filial", description = "Fazer a atualização das filiais",
        responses = {
                @ApiResponse(responseCode = "200"),
                @ApiResponse(responseCode = "400"),
                @ApiResponse(responseCode = "404")
        }
    )
    public ResponseEntity<FilialResponse> update(@PathVariable UUID id, @Valid @RequestBody FilialRequest input) {
        var updated = filialService.update(id, input);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remover filiais", description = "Atualiza a flag de filial com base no ID fornecido.", responses = {
            @ApiResponse(responseCode = "204", description = "Removido com sucesso."),
            @ApiResponse(responseCode = "400", description = "Requisição inválida."),
            @ApiResponse(responseCode = "404", description = "Registro não encontrado.")
    })
    public void delete(@PathVariable UUID id) {
        this.filialService.delete(id);
    }

    @Cacheable("filiais")
    @GetMapping("/{id}")
    @Operation(summary = "Buscar Filial", description = "Busca registros de Manutenção com base no ID fornecido.",  responses = {
            @ApiResponse(responseCode = "200", description = "Encontrado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Requisição inválida."),
            @ApiResponse(responseCode = "404", description = "Registro não encontrado.")
    })
    public ResponseEntity<FilialResponse> get(@PathVariable UUID id) {
        var found = filialService.getById(id);
        return ResponseEntity.ok(found);
    }



    @GetMapping("/all")
    @Operation(summary = "Buscar filiais", description = "Fazer a busca de todas as filiais",
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "404")
            }
    )
    public ResponseEntity<List<FilialResponse>> geAll() {
        var found = filialService.findAll();
        return ResponseEntity.ok(found);
    }
}
