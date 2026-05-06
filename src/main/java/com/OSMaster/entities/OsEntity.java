package com.OSMaster.entities;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class OsEntity {

    private Long OsNumber;
    private String title;
    private String description;
    private String address;
    private LocalDate date;
    private Boolean isCompleted;
}
