package com.OSMaster.datasource.dataobjects;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "log")
public class LogDataEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    @Column(name = "payload", length = 1000, nullable = false)
    private String payload;
    @Column(name = "response", length = 1000, nullable = true)
    private String response;
    @Column(name = "dispatchDate", nullable = false)
    private LocalDateTime dispatchDate;
    @Column(name = "responseDate", nullable = true)
    private LocalDateTime responseDate;
    @Column(name = "returnCode", nullable = true)
    private int returnCode;
}
