package br.com.fiap.mottu_challenge.service;

import br.com.fiap.mottu_challenge.dto.request.OperadorRequest;
import br.com.fiap.mottu_challenge.dto.response.OperadorResponse;
import br.com.fiap.mottu_challenge.model.Operador;
import br.com.fiap.mottu_challenge.repository.OperadorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OperadorServiceTest {

    @Mock
    private OperadorRepository operadorRepository;

    @InjectMocks
    private OperadorService operadorService;

    private UUID operadorId;
    private Operador operador;
    private OperadorRequest operadorRequest;

    @BeforeEach
    void setUp() {
        operadorId = UUID.randomUUID();
        LocalDate dataNascimento = LocalDate.of(1990, 1, 1);

        operador = Operador.builder()
                .idOperador(operadorId)
                .nome("Operador Teste")
                .cpf("123.456.789-00")
                .rg("1234567")
                .dataNascimento(dataNascimento)
                .build();

        operadorRequest = new OperadorRequest();
        operadorRequest.setNome("Operador Teste");
        operadorRequest.setCpf("123.456.789-00");
        operadorRequest.setRg("1234567");
        operadorRequest.setDataNascimento(dataNascimento);
    }

    @Test
    @DisplayName("Deve criar uma lista de operadores com sucesso")
    void create_WhenGivenList_ShouldSaveAllAndReturnResponseList() {
        List<OperadorRequest> requestList = List.of(operadorRequest);
        List<Operador> operadorList = List.of(operador);

        when(operadorRepository.saveAll(anyList())).thenReturn(operadorList);

        List<OperadorResponse> responseList = operadorService.create(requestList);

        assertNotNull(responseList);
        assertFalse(responseList.isEmpty());
        assertEquals(1, responseList.size());
        assertEquals("Operador Teste", responseList.get(0).getNome());
        verify(operadorRepository).saveAll(anyList());
    }

    @Test
    @DisplayName("Deve atualizar um operador e retornar status 200 OK")
    void updateById_WhenOperadorExists_ShouldUpdateAndReturnOkResponse() {
        OperadorRequest updateRequest = new OperadorRequest();
        updateRequest.setNome("Nome Atualizado");
        updateRequest.setCpf(operadorRequest.getCpf());
        updateRequest.setRg(operadorRequest.getRg());
        updateRequest.setDataNascimento(operadorRequest.getDataNascimento());

        when(operadorRepository.findById(operadorId)).thenReturn(Optional.of(operador));
        when(operadorRepository.save(any(Operador.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ResponseEntity<OperadorResponse> responseEntity = operadorService.updateById(operadorId, updateRequest);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("Nome Atualizado", responseEntity.getBody().getNome());
        assertEquals(operadorId, responseEntity.getBody().getId());

        verify(operadorRepository).findById(operadorId);
        verify(operadorRepository).save(any(Operador.class));
    }

    @Test
    @DisplayName("Deve lançar NoSuchElementException ao tentar deletar operador que não existe")
    void deleteById_WhenOperadorNotFound_ShouldThrowAndNotDelete() {
        UUID idInexistente = UUID.randomUUID();
        when(operadorRepository.findById(idInexistente)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            operadorService.deleteById(idInexistente);
        });

        verify(operadorRepository).findById(idInexistente);
        verify(operadorRepository, never()).deleteById(any(UUID.class));
    }

    @Test
    @DisplayName("Deve lançar NoSuchElementException quando findAll não retornar operadores")
    void findAll_WhenRepositoryIsEmpty_ShouldThrowNoSuchElementException() {
        when(operadorRepository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(NoSuchElementException.class, () -> {
            operadorService.findAll();
        });

        verify(operadorRepository).findAll();
    }
}