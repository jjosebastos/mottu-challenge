package br.com.fiap.mottu_challenge.controller;

import br.com.fiap.mottu_challenge.dto.request.EnderecoRequest;
import br.com.fiap.mottu_challenge.dto.request.EnderecoRequestList;
import br.com.fiap.mottu_challenge.dto.response.EnderecoResponse;
import br.com.fiap.mottu_challenge.model.Endereco;
import br.com.fiap.mottu_challenge.model.specification.EnderecoFilter;
import br.com.fiap.mottu_challenge.repository.EnderecoRepository;
import br.com.fiap.mottu_challenge.service.EnderecoService;
import br.com.fiap.mottu_challenge.specification.EnderecoSpecification;
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
@RequestMapping("/endereco")
public class  EnderecoController {

    @Autowired
    private EnderecoService enderecoService;
    @Autowired
    private EnderecoRepository enderecoRepository;


    @GetMapping
    @Operation(summary = "Busca Endereços Specification", description = "Busca endereços com base em filters",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Encontrado com sucesso."),
                    @ApiResponse(responseCode = "400", description = "ID fornecido inválido."),
                    @ApiResponse(responseCode = "404", description = "Endereco não encontrado.")
            })
    public Page<Endereco> index(
            EnderecoFilter filter,
            @PageableDefault(size = 10, sort = "cidade", direction = Sort.Direction.DESC) Pageable pageable
    ){
        var specification = EnderecoSpecification.withFilters(filter);
        return enderecoRepository.findAll(specification, pageable);
    }

    @PostMapping
    @CacheEvict(value = "enderecos", allEntries = true)
    @Operation(summary = "Cria endereços", description = "Cria endereços de filiais",
    responses = {
        @ApiResponse(responseCode = "201", description = "Endereço criado com sucesso."),
        @ApiResponse(responseCode = "400", description = "Requisição inválida."),
        @ApiResponse(responseCode = "404", description = "ID Filial inválido.")
    })
    public ResponseEntity<List<EnderecoResponse>> create(@Valid @RequestBody  EnderecoRequestList endereco) {
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
    public ResponseEntity<EnderecoResponse> update(@PathVariable UUID id,@RequestBody @Valid EnderecoRequest endereco) {
        var updated = this.enderecoService.update(id, endereco);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remove Endereços", description = "Remove o Endereço com base no ID fornecido.",
        responses = {
            @ApiResponse(responseCode = "204", description = "Endereco removido com sucesso."),
            @ApiResponse(responseCode = "400", description = "ID fornecido invalido."),
            @ApiResponse(responseCode = "404", description = "Endereco não encontrada.")
    })
    public void deleteById(@PathVariable UUID id) {
        this.enderecoService.delete(id);
    }

    @GetMapping("/all")
    @Operation(summary = "Buscar Enderecos", description = "Fazer a busca de todas as filiais",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Encontrado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Endereços não encontrados")
            }
    )
    public ResponseEntity<List<EnderecoResponse>> findAll() {
        var found = this.enderecoService.findAll();
        return ResponseEntity.ok(found);
    }

    @GetMapping("/{id}")
    @Cacheable("enderecos")
    @Operation(summary = "Busca Endereços", description = "Busca endereços com base no ID fornecido",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Encontrado com sucesso."),
                    @ApiResponse(responseCode = "400", description = "ID fornecido inválido."),
                    @ApiResponse(responseCode = "404", description = "Endereco não encontrado.")
            })
    public ResponseEntity<Endereco> getById(@PathVariable UUID id) {
        var found = this.enderecoService.getById(id);
        return ResponseEntity.ok(found);
    }

}
