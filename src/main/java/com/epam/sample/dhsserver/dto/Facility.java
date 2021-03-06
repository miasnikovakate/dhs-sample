package com.epam.sample.dhsserver.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Facility {

    private String id;
    private String practiceId;
    private String name;
    private String city;
    private Long lastUpdated;
}
