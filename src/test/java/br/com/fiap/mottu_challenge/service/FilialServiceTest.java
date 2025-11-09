package br.com.fiap.mottu_challenge.service;

import br.com.fiap.mottu_challenge.dto.request.FilialRequest;
import br.com.fiap.mottu_challenge.dto.request.FilialRequestList;
import br.com.fiap.mottu_challenge.dto.response.FilialResponse;
import br.com.fiap.mottu_challenge.model.Filial;
import br.com.fiap.mottu_challenge.model.enums.CodPais;
import br.com.fiap.mottu_challenge.repository.FilialRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FilialServiceTest {

    @Mock
    private FilialRepository repository;

    @Mock
    private ExpoNotificationService notificationService;

    @InjectMocks
    private FilialService filialService;

    private Filial filial;
    private FilialRequest filialRequest;
    private UUID filialId;

    @BeforeEach
    void setUp() {
        filialId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");

        filial = Filial.builder()
                .id(filialId)
                .nome("Filial Central")
                .cnpj("12345678000199")
                .codPais(CodPais.BR)
                .dataAbertura(LocalDate.now())
                .build();

        filialRequest = new FilialRequest();
        filialRequest.setNome("Filial Central");
        filialRequest.setCnpj("12345678000199");
        filialRequest.setCdPais(CodPais.BR);
        filialRequest.setDataAbertura(LocalDate.now());
    }

    @Test
    @DisplayName("Deve retornar uma filial quando o ID existir")
    void getById_WhenFilialExists_ShouldReturnFilialResponse() {
        when(repository.findById(filialId)).thenReturn(Optional.of(filial));

        FilialResponse response = filialService.getById(filialId);

        assertNotNull(response);
        assertEquals(filialId, response.getIdFilial());
        assertEquals("Filial Central", response.getNome());
        verify(repository).findById(filialId);
    }

    @Test
    @DisplayName("Deve lançar NoSuchElementException quando o ID não existir")
    void getById_WhenFilialDoesNotExist_ShouldThrowException() {
        UUID idInexistente = UUID.randomUUID();
        when(repository.findById(idInexistente)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            filialService.getById(idInexistente);
        });

        verify(repository).findById(idInexistente);
    }

    @Test
    @DisplayName("Deve criar a filial e enviar notificação com sucesso")
    void create_WhenGivenValidList_ShouldSaveAndSendNotification() {
        FilialRequestList requestList = new FilialRequestList(List.of(filialRequest));

        when(repository.saveAll(anyList())).thenReturn(List.of(filial));
        
        doNothing().when(notificationService).sendNotificationToAll(anyString(), anyString());

        List<FilialResponse> responses = filialService.create(requestList);

        assertNotNull(responses);
        assertFalse(responses.isEmpty());
        assertEquals(1, responses.size());
        assertEquals("Filial Central", responses.get(0).getNome());

        verify(repository).saveAll(anyList());
        verify(notificationService).sendNotificationToAll(
                eq("Nova Filial Cadastrada!"),
                eq("Uma nova filial (Filial Central) foi adicionada!")
        );
    }
    
    @Test
    @DisplayName("Deve criar a filial mesmo se o envio da notificação falhar")
    void create_WhenNotificationFails_ShouldStillSaveAndNotThrow() {
        FilialRequestList requestList = new FilialRequestList(List.of(filialRequest));

        when(repository.saveAll(anyList())).thenReturn(List.of(filial));
        
        doThrow(new RuntimeException("Falha ao conectar no Expo"))
             .when(notificationService)
             .sendNotificationToAll(anyString(), anyString());

        List<FilialResponse> responses = assertDoesNotThrow(() -> {
            return filialService.create(requestList);
        }, "O serviço não deveria lançar exceção se a notificação falhar");
        
        assertNotNull(responses);
        assertFalse(responses.isEmpty());
        
        verify(repository).saveAll(anyList());
        verify(notificationService).sendNotificationToAll(anyString(), anyString());
    }
}