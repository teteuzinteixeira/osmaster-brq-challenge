package com.OSMaster.repository;

import com.OSMaster.entities.OsEntity;
import org.springframework.stereotype.Component;

@Component
public interface OsRepository {
    OsEntity save(OsEntity osEntity);
    void completeById(Long id);
}
