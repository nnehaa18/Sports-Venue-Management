package com.assignment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SportApiDTO {
    @JsonProperty("sport_id")
    private Long sportId;
    @JsonProperty("sport_code")
    private String sportCode;
    @JsonProperty("sport_name")
    private String sportName;
}
