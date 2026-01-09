package com.assignment.dto;

import lombok.Data;

import java.util.Set;

@Data
public class VenueRequestDTO {
    private String name;
    private String location;
    private Set<String> sportCodes;
}
