package com.OSMaster.interactors.impl;

import com.OSMaster.interactors.CompleteOsInteractor;
import com.OSMaster.repository.OsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CompleteOsInteractorImpl implements CompleteOsInteractor {

    private final OsRepository osRepository;

    @Override
    public void completeOs(Long id) {
        osRepository.completeById(id);
    }
}
