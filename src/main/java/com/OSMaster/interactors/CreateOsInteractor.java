package com.OSMaster.interactors;

import com.OSMaster.entities.OsEntity;
import com.OSMaster.transportlayer.dto.request.CreateOsRequest;

public interface CreateOsInteractor {
    OsEntity createOs(CreateOsRequest dto);
}
