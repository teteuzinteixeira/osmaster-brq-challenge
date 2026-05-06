package com.OSMaster.datasource.dataobjects;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "OrdemServico")
public class OsDataEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    @Column(name = "title", length = 150, nullable = false)
    private String title;
    @Column(name = "description", length = 255, nullable = false)
    private String description;
    @Column(name = "address", length = 500, nullable = false)
    private String address;
    @Column(name = "date", nullable = false)
    private LocalDate date;
    @Column(name = "isCompleted", nullable = false)
    private Boolean isCompleted;
}
