package com.OSMaster.interactors.impl;

import com.OSMaster.entities.LogEntity;
import com.OSMaster.repository.LogRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tools.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LogInteractorImplTest {

    @Mock
    private LogRepository logRepository;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private LogInteractorImpl logInteractor;

    @Test
    void shouldIniciarLogSuccessfully() throws Exception {
        Object payload = new Object();
        String jsonPayload = "{\"data\":\"test\"}";

        when(objectMapper.writeValueAsString(payload)).thenReturn(jsonPayload);
        when(logRepository.save(any(LogEntity.class))).thenAnswer(i -> i.getArgument(0));

        LogEntity result = logInteractor.iniciarLog(payload);

        assertNotNull(result);
        assertEquals(jsonPayload, result.getPayload());
        assertNotNull(result.getDispatchDate());
        verify(logRepository).save(any(LogEntity.class));
    }

    @Test
    void shouldFinalizarLogSuccessfully() throws Exception {
        LogEntity log = new LogEntity();
        Object responseObj = new Object();
        String jsonResponse = "{\"status\":\"ok\"}";
        int statusCode = 200;

        when(objectMapper.writeValueAsString(responseObj)).thenReturn(jsonResponse);

        logInteractor.finalizarLog(log, statusCode, responseObj);

        assertEquals(statusCode, log.getReturnCode());
        assertEquals(jsonResponse, log.getResponse());
        assertNotNull(log.getResponseDate());
        verify(logRepository).save(log);
    }

    @Test
    void shouldThrowExceptionWhenJsonFails() throws Exception {
        Object payload = new Object();
        when(objectMapper.writeValueAsString(any())).thenThrow(new RuntimeException("Json Error"));

        assertThrows(RuntimeException.class, () -> logInteractor.iniciarLog(payload));
        verify(logRepository, never()).save(any());
    }
}