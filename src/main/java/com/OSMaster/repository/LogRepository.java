package com.OSMaster.repository;

import com.OSMaster.entities.LogEntity;
import org.springframework.stereotype.Component;

@Component
public interface LogRepository {
    LogEntity save(LogEntity logEntity);
}
