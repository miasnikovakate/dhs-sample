package com.epam.sample.dhsserver.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Practice {
    private String id;
    private String name;
    private Long lastUpdated;
}
