package com.OSMaster.interactors.impl;

import com.OSMaster.entities.OsEntity;
import com.OSMaster.interactors.CreateOsInteractor;
import com.OSMaster.repository.OsRepository;
import com.OSMaster.transportlayer.api.out.CepOut;
import com.OSMaster.transportlayer.dto.request.CreateOsRequest;
import com.OSMaster.transportlayer.dto.response.ConsultaCepResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateOsInteractorImpl implements CreateOsInteractor {

    private final CepOut cepOut;
    private final OsRepository osRepository;

    @Override
    public OsEntity createOs(CreateOsRequest dto) {
        var cepResponse = cepOut.consultaCep(dto.getCep());
        var mapped = mapToOsEntity(dto, cepResponse);

        return osRepository.save(mapped);
    }

    private OsEntity mapToOsEntity(CreateOsRequest dto, ConsultaCepResponse cepResponse) {
        var osEntity = new OsEntity();
        osEntity.setTitle(dto.getTitle());
        osEntity.setDescription(dto.getDescription());
        osEntity.setAddress(cepResponse.getLogradouro() + ", " +
                cepResponse.getNumero() + ", " +
                cepResponse.getBairro() + ", " +
                cepResponse.getCidade() + " - " +
                cepResponse.getEstado());
        osEntity.setDate(dto.getDate());
        osEntity.setIsCompleted(false);
        return osEntity;
    }
}
