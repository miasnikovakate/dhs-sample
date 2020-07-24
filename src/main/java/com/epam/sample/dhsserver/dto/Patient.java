package com.epam.sample.dhsserver.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@Accessors(chain = true)
public class Patient {
    private String id;
    private String facilityId;
    private String surgeonId;
    private String practiceId;
    private String name;
    private LocalDate birthDate;
    private Long lastUpdated;
}
