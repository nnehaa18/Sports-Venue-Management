package com.assignment.mapper;

import com.assignment.dto.VenueResponseDTO;
import com.assignment.entities.Venue;
import org.springframework.stereotype.Component;

@Component
public class VenueMapper {
    private VenueMapper() {}

    public static VenueResponseDTO mapResponse (Venue venue) {
        if(venue == null) {
            return null;
        }
        VenueResponseDTO venueResponseDTO = new VenueResponseDTO();
        venueResponseDTO.setId(venue.getId());
        venueResponseDTO.setName(venue.getVenueName());
        venueResponseDTO.setLocation(venue.getLocation());
        return venueResponseDTO;
    }
}
