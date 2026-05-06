package com.OSMaster.datasource.repositoryImpl;

import com.OSMaster.datasource.jpa.LogJpaRepository;
import com.OSMaster.datasource.mappers.LogMapper;
import com.OSMaster.entities.LogEntity;
import com.OSMaster.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LogRepositoryImpl implements LogRepository {

    private final LogJpaRepository logJpaRepository;
    private final LogMapper logMapper;

    @Override
    public LogEntity save(LogEntity logEntity) {
        var dataEntity = logMapper.toDataEntity(logEntity);
        var savedDataEntity = logJpaRepository.save(dataEntity);
        return logMapper.toDomainEntity(savedDataEntity);
    }
}
