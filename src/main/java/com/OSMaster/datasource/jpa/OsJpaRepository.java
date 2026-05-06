package com.OSMaster.datasource.jpa;

import com.OSMaster.datasource.dataobjects.OsDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface OsJpaRepository extends JpaRepository<OsDataEntity, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE OsDataEntity o SET o.isCompleted = true WHERE o.id = :id")
    void completeById(@Param("id") Long id);
}
