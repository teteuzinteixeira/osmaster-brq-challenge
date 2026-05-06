package com.OSMaster.repository;

import com.OSMaster.entities.OsEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface OsRepository {
    OsEntity save(OsEntity osEntity);
    void completeById(Long id);
    List<OsEntity> findAllByDate();
}
