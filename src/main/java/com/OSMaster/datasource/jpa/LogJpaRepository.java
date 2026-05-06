package com.OSMaster.datasource.jpa;

import com.OSMaster.datasource.dataobjects.LogDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogJpaRepository extends JpaRepository<LogDataEntity, Long> {
}
