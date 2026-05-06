package com.OSMaster.interactors.impl;

import com.OSMaster.repository.OsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CompleteOsInteractorImplTest {

    @Mock
    private OsRepository osRepository;

    @InjectMocks
    private CompleteOsInteractorImpl completeOsInteractor;

    @Test
    void shouldInvokeRepositoryToCompleteOs() {
        Long id = 123L;

        completeOsInteractor.completeOs(id);

        verify(osRepository, times(1)).completeById(id);
    }
}