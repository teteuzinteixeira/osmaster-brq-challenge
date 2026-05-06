package com.OSMaster.datasource.repositoryImpl;

import com.OSMaster.datasource.dataobjects.LogDataEntity;
import com.OSMaster.datasource.jpa.LogJpaRepository;
import com.OSMaster.datasource.mappers.LogMapper;
import com.OSMaster.entities.LogEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LogRepositoryImplTest {

    @Mock
    private LogJpaRepository logJpaRepository;

    @Mock
    private LogMapper logMapper;

    @InjectMocks
    private LogRepositoryImpl logRepositoryImpl;

    @Test
    void shouldSaveLogSuccessfully() {
        LogEntity logInput = new LogEntity();
        LogDataEntity dataEntity = new LogDataEntity();
        LogDataEntity savedDataEntity = new LogDataEntity();
        LogEntity expectedResponse = new LogEntity();

        when(logMapper.toDataEntity(logInput)).thenReturn(dataEntity);
        when(logJpaRepository.save(dataEntity)).thenReturn(savedDataEntity);
        when(logMapper.toDomainEntity(savedDataEntity)).thenReturn(expectedResponse);

        LogEntity result = logRepositoryImpl.save(logInput);

        assertNotNull(result);
        assertEquals(expectedResponse, result);

        verify(logMapper, times(1)).toDataEntity(logInput);
        verify(logJpaRepository, times(1)).save(dataEntity);
        verify(logMapper, times(1)).toDomainEntity(savedDataEntity);
    }
}