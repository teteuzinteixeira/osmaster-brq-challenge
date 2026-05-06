package com.OSMaster.interactors.impl;

import com.OSMaster.entities.OsEntity;
import com.OSMaster.repository.OsRepository;
import com.OSMaster.transportlayer.api.out.CepOut;
import com.OSMaster.transportlayer.dto.request.CreateOsRequest;
import com.OSMaster.transportlayer.dto.response.ConsultaCepResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateOsInteractorImplTest {

    @Mock
    private CepOut cepOut;

    @Mock
    private OsRepository osRepository;

    @InjectMocks
    private CreateOsInteractorImpl createOsInteractor;

    @Test
    void shouldCreateOsWithFormattedAddress() {
        CreateOsRequest request = new CreateOsRequest();
        request.setCep("12345678");
        request.setTitle("Reparo de Goteira");
        request.setDescription("Cozinha");
        request.setDate(LocalDate.now());

        ConsultaCepResponse cepResponse = new ConsultaCepResponse();
        cepResponse.setLogradouro("Rua das Flores");
        cepResponse.setNumero(100);
        cepResponse.setBairro("Jardim");
        cepResponse.setCidade("São Paulo");
        cepResponse.setEstado("SP");

        when(cepOut.consultaCep(request.getCep())).thenReturn(cepResponse);
        when(osRepository.save(any(OsEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        OsEntity result = createOsInteractor.createOs(request);

        assertNotNull(result);
        assertEquals("Reparo de Goteira", result.getTitle());
        assertFalse(result.getIsCompleted());

        String expectedAddress = "Rua das Flores, 100, Jardim, São Paulo - SP";
        assertEquals(expectedAddress, result.getAddress());

        verify(cepOut).consultaCep("12345678");
        verify(osRepository).save(any(OsEntity.class));
    }

    @Test
    void shouldThrowExceptionWhenCepFails() {
        CreateOsRequest request = new CreateOsRequest();
        request.setCep("00000000");

        when(cepOut.consultaCep(anyString())).thenThrow(new RuntimeException("CEP não encontrado"));

        assertThrows(RuntimeException.class, () -> createOsInteractor.createOs(request));
        verify(osRepository, never()).save(any());
    }
}