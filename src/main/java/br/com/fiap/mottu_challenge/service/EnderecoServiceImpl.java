package br.com.fiap.mottu_challenge.service;

import br.com.fiap.mottu_challenge.dto.request.EnderecoRequest;
import br.com.fiap.mottu_challenge.dto.request.EnderecoRequestList;
import br.com.fiap.mottu_challenge.dto.response.EnderecoResponse;
import br.com.fiap.mottu_challenge.dto.response.FilialResponse;
import br.com.fiap.mottu_challenge.model.Endereco;
import br.com.fiap.mottu_challenge.model.Filial;
import br.com.fiap.mottu_challenge.repository.EnderecoRepository;
import br.com.fiap.mottu_challenge.repository.FilialRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EnderecoServiceImpl implements EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private FilialRepository filialRepository;

    @Transactional
    @Override
    public ResponseEntity<List<EnderecoResponse>> create(EnderecoRequestList input) {
        var enderecosList = input.getEnderecosList();
        if(enderecosList.isEmpty()){
            throw new IllegalArgumentException();
        }
        List<EnderecoResponse> result  = new ArrayList<>();
        for (EnderecoRequest endereco : enderecosList) {
            UUID enderecoId = endereco.getUuid();
            if(Objects.isNull(enderecoId)){
                throw new IllegalArgumentException();
            }
            Optional<Filial> enderecoOptional = filialRepository.findById(enderecoId);
            if(enderecoOptional.isEmpty()){
                throw new NoSuchElementException();
            }
            Endereco mapped = this.enderecoMapper(endereco);
            mapped.setFilial(enderecoOptional.get());
            var saved = this.enderecoRepository.save(mapped);
            result.add(this.toFilialResponse(saved));
        }
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<EnderecoResponse> getById(UUID id) {
        if(Objects.isNull(id)){
            throw new IllegalArgumentException();
        }
        var found = this.enderecoRepository.findById(id);
        if(found.isEmpty()){
            throw new NoSuchElementException();
        }
        var mapped = this.toFilialResponse(found.get());
        return ResponseEntity.ok(mapped);
    }

    @Override
    public void delete(UUID id) {
        if(id == null){
            throw new IllegalArgumentException();
        }
        this.enderecoRepository.deleteById(id);
    }

    @Override
    public ResponseEntity<EnderecoResponse> update(UUID id, EnderecoRequest input) {
        if(Objects.isNull(id)){
            throw new IllegalArgumentException();
        }
        var found = this.enderecoRepository.findById(id);
        if(found.isEmpty()){
            throw new NoSuchElementException();
        }
        var endereco = found.get();
        endereco.setLogradouro(input.getLogradouro());
        endereco.setNumero(input.getNumero());
        endereco.setCidade(input.getCidade());
        endereco.setBairro(input.getBairro());
        endereco.setComplemento(input.getComplemento());
        endereco.setUf(input.getUf());
        endereco.setCep(input.getCep());

        var response = this.toFilialResponse(endereco);
        return ResponseEntity.ok(response);
    }

    private Endereco enderecoMapper(EnderecoRequest endereco) {
        return Endereco
                .builder()
                .logradouro(endereco.getLogradouro())
                .numero(endereco.getNumero())
                .bairro(endereco.getBairro())
                .cidade(endereco.getCidade())
                .cep(endereco.getCep())
                .complemento(endereco.getComplemento())
                .build();
    }

    private EnderecoResponse toFilialResponse(Endereco endereco) {
        return EnderecoResponse.builder()
                .idEndereco(endereco.getIdEndereco())
                .logradouro(endereco.getLogradouro())
                .numero(endereco.getNumero())
                .bairro(endereco.getBairro())
                .cidade(endereco.getCidade())
                .uf(endereco.getUf())
                .cep(endereco.getCep())
                .complemento(endereco.getComplemento())
                .idFilial(endereco.getFilial().getId())
                .build();
    }
}
