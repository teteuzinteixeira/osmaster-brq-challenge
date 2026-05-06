package com.OSMaster.datasource.mappers;

import com.OSMaster.datasource.dataobjects.OsDataEntity;
import com.OSMaster.entities.OsEntity;
import org.springframework.stereotype.Component;

@Component
public class OsMapper {

    public OsDataEntity toDataEntity(OsEntity osEntity) {
        var dataEntity = new OsDataEntity();
        dataEntity.setTitle(osEntity.getTitle());
        dataEntity.setDescription(osEntity.getDescription());
        dataEntity.setAddress(osEntity.getAddress());
        dataEntity.setDate(osEntity.getDate());
        dataEntity.setIsCompleted(osEntity.getIsCompleted());
        return dataEntity;
    }

    public OsEntity toDomainEntity(OsDataEntity dataEntity) {
        var osEntity = new OsEntity();
        osEntity.setOsNumber(dataEntity.getId());
        osEntity.setTitle(dataEntity.getTitle());
        osEntity.setDescription(dataEntity.getDescription());
        osEntity.setAddress(dataEntity.getAddress());
        osEntity.setDate(dataEntity.getDate());
        osEntity.setIsCompleted(dataEntity.getIsCompleted());
        return osEntity;
    }
}
