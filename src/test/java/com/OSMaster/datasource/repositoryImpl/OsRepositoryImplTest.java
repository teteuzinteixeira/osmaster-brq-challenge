package com.OSMaster.datasource.repositoryImpl;

import com.OSMaster.datasource.dataobjects.OsDataEntity;
import com.OSMaster.datasource.jpa.OsJpaRepository;
import com.OSMaster.datasource.mappers.OsMapper;
import com.OSMaster.entities.OsEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OsRepositoryImplTest {

    @Mock
    private OsJpaRepository osJpaRepository;

    @Mock
    private OsMapper osMapper;

    @InjectMocks
    private OsRepositoryImpl osRepository;

    @Test
    void shouldSaveOsSuccessfully() {
        OsEntity osEntity = new OsEntity();
        OsDataEntity dataEntity = new OsDataEntity();
        OsDataEntity savedData = new OsDataEntity();
        OsEntity expected = new OsEntity();

        when(osMapper.toDataEntity(osEntity)).thenReturn(dataEntity);
        when(osJpaRepository.save(dataEntity)).thenReturn(savedData);
        when(osMapper.toDomainEntity(savedData)).thenReturn(expected);

        OsEntity result = osRepository.save(osEntity);

        assertNotNull(result);
        assertEquals(expected, result);
        verify(osJpaRepository).save(dataEntity);
    }

    @Test
    void shouldCompleteByIdSuccessfully() {
        Long id = 1L;
        doNothing().when(osJpaRepository).completeById(id);

        osRepository.completeById(id);

        verify(osJpaRepository, times(1)).completeById(id);
    }

    @Test
    void shouldFindAllByDateSuccessfully() {
        LocalDate today = LocalDate.now();
        List<OsDataEntity> dataEntities = List.of(new OsDataEntity());
        List<OsEntity> domainEntities = List.of(new OsEntity());

        when(osJpaRepository.findAllByDate(today)).thenReturn(dataEntities);
        when(osMapper.toDomainEntity(dataEntities)).thenReturn(domainEntities);

        List<OsEntity> result = osRepository.findAllByDate();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(domainEntities, result);
        verify(osJpaRepository).findAllByDate(today);
    }
}