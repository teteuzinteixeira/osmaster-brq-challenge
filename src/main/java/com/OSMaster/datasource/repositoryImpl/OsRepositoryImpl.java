package com.OSMaster.datasource.repositoryImpl;

import com.OSMaster.datasource.jpa.OsJpaRepository;
import com.OSMaster.datasource.mappers.OsMapper;
import com.OSMaster.entities.OsEntity;
import com.OSMaster.repository.OsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OsRepositoryImpl implements OsRepository {

    private final OsJpaRepository osJpaRepository;
    private final OsMapper osMapper;

    @Override
    public OsEntity save(OsEntity osEntity) {
        var dataEntity = osMapper.toDataEntity(osEntity);
        var savedDataEntity = osJpaRepository.save(dataEntity);
        return osMapper.toDomainEntity(savedDataEntity);
    }

    @Override
    public void completeById(Long id) {
        osJpaRepository.completeById(id);
    }
}
