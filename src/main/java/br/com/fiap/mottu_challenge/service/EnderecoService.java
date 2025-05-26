package br.com.fiap.mottu_challenge.service;

import br.com.fiap.mottu_challenge.dto.request.EnderecoRequest;
import br.com.fiap.mottu_challenge.dto.request.EnderecoRequestList;
import br.com.fiap.mottu_challenge.dto.response.EnderecoResponse;
import br.com.fiap.mottu_challenge.model.Endereco;
import br.com.fiap.mottu_challenge.model.Filial;
import br.com.fiap.mottu_challenge.repository.EnderecoRepository;
import br.com.fiap.mottu_challenge.repository.FilialRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.*;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private FilialRepository filialRepository;

    @Transactional
    public List<EnderecoResponse> create(EnderecoRequestList input) {
        var enderecosList = input.getEnderecosList();
        if(enderecosList.isEmpty()){
            throw new IllegalArgumentException();
        }
        List<EnderecoResponse> result  = new ArrayList<>();
        for (EnderecoRequest endereco : enderecosList) {
            var filialId = endereco.getIdFilial();
            if(Objects.isNull(filialId)){
                throw new IllegalArgumentException();
            }
            var enderecoOptional = filialRepository.findById(filialId)
                    .orElseThrow(NoSuchElementException::new);
            Endereco mapped = this.enderecoMapper(endereco);
            mapped.setFilial(enderecoOptional);
            var saved = this.enderecoRepository.save(mapped);
            result.add(this.toFilialResponse(saved));
        }
        return result;
    }

    public Endereco getById(UUID id) {
        return this.enderecoRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
    }

    @Transactional
    public void delete(UUID id) {
        this.enderecoRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
        this.enderecoRepository.deleteById(id);
    }

    @Transactional
    public EnderecoResponse update(UUID id, EnderecoRequest input) {
        if(Objects.isNull(id)){
            throw new IllegalArgumentException();
        }
        var found = this.enderecoRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
        found.setLogradouro(input.getLogradouro());
        found.setNumero(input.getNumero());
        found.setCidade(input.getCidade());
        found.setBairro(input.getBairro());
        found.setComplemento(input.getComplemento());
        found.setUf(input.getUf());
        found.setCep(input.getCep());

        var updated = this.enderecoRepository.save(found);

        return this.toFilialResponse(updated);
    }

    public List<EnderecoResponse> findAll() {
        var enderecos = this.enderecoRepository.findAll();
        if(enderecos.isEmpty()) {
            throw new NoSuchElementException();

        }
        return enderecos.stream()
                .map(this::toFilialResponse)
                .toList();
    }

    private Endereco enderecoMapper(EnderecoRequest request) {
        return Endereco
                .builder()
                .logradouro(request.getLogradouro())
                .numero(request.getNumero())
                .bairro(request.getBairro())
                .cidade(request.getCidade())
                .uf(request.getUf())
                .cep(request.getCep())
                .complemento(request.getComplemento())
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
