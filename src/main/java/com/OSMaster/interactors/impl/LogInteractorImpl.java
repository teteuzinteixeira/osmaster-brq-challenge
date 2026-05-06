package com.OSMaster.interactors.impl;

import com.OSMaster.entities.LogEntity;
import com.OSMaster.interactors.LogInteractor;
import com.OSMaster.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LogInteractorImpl implements LogInteractor {

    private final LogRepository logRepository;
    private final ObjectMapper objectMapper;

    public LogEntity iniciarLog(Object payload) {
        var log = new LogEntity();
        log.setPayload(toJson(payload));
        log.setDispatchDate(LocalDateTime.now());
        return logRepository.save(log);
    }

    public void finalizarLog(LogEntity log, int statusCode, Object response) {
        log.setReturnCode(statusCode);
        log.setResponse(toJson(response));
        log.setResponseDate(LocalDateTime.now());
        logRepository.save(log);
    }

    private String toJson(Object obj) {
        return objectMapper.writeValueAsString(obj);
    }
}
