package com.OSMaster.datasource.mappers;

import com.OSMaster.datasource.dataobjects.LogDataEntity;
import com.OSMaster.entities.LogEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class LogMapper {

    public LogDataEntity toDataEntity(LogEntity entity) {
        var dataEntity = new LogDataEntity();
        Optional.ofNullable(entity.getId())
                .map(UUID::fromString)
                .ifPresent(dataEntity::setId);
        dataEntity.setPayload(entity.getPayload());
        dataEntity.setResponse(entity.getResponse());
        dataEntity.setDispatchDate(entity.getDispatchDate());
        dataEntity.setResponseDate(entity.getResponseDate());
        dataEntity.setReturnCode(entity.getReturnCode());
        return dataEntity;
    }

    public LogEntity toDomainEntity(LogDataEntity entity) {
        var dataEntity = new LogEntity();
        dataEntity.setId(String.valueOf(entity.getId()));
        dataEntity.setPayload(entity.getPayload());
        dataEntity.setResponse(entity.getResponse());
        dataEntity.setDispatchDate(entity.getDispatchDate());
        dataEntity.setResponseDate(entity.getResponseDate());
        dataEntity.setReturnCode(entity.getReturnCode());
        return dataEntity;
    }
}
