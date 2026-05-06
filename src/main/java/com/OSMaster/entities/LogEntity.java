package com.OSMaster.entities;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class LogEntity {
    private String id;
    private String payload;
    private String response;
    private LocalDateTime dispatchDate;
    private LocalDateTime responseDate;
    private int returnCode;
}
