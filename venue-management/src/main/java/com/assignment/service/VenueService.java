package com.assignment.service;

import com.assignment.dao.SportRepository;
import com.assignment.dao.VenueRepository;
import com.assignment.dto.VenueRequestDTO;
import com.assignment.dto.VenueResponseDTO;
import com.assignment.entities.Sport;
import com.assignment.entities.Venue;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class VenueService {
    private final VenueRepository venueRepository;
    private final SportRepository sportRepository;

    public VenueService (VenueRepository venueRepository, SportRepository sportRepository) {
        this.venueRepository = venueRepository;
        this.sportRepository = sportRepository;
    }

    /**
     * add a venue
     * @param venueRequestDTO
     * @return
     */
    public VenueResponseDTO addVenue(VenueRequestDTO venueRequestDTO) {
        Venue venue = new Venue();
        venue.setVenueName(venueRequestDTO.getName());
        venue.setLocation(venueRequestDTO.getLocation());
        Set<Sport> sports = sportRepository.findBySportCodeIn(venueRequestDTO.getSportCodes())
                .stream().collect(Collectors.toSet());
        venue.setSports(sports);
        venueRepository.save(venue);
        return mapVenueToResponseDTO(venue);
    }

    /**
     * Venue to dto mapper for generating response
     * @param venue
     * @return
     */
    public VenueResponseDTO mapVenueToResponseDTO (Venue venue) {
        VenueResponseDTO venueResponseDTO = new VenueResponseDTO();
        venueResponseDTO.setId(venue.getId());
        venueResponseDTO.setName(venue.getVenueName());
        venueResponseDTO.setLocation(venue.getLocation());
        venueResponseDTO.setSportCodes(venue.getSports().stream()
                .map(Sport::getSportCode).collect(Collectors.toSet()));
        return venueResponseDTO;
    }

    /**
     * list all venues
     * @return
     */
    public List<VenueResponseDTO> listVenues () {
        return venueRepository.findAll().stream()
                .map(this::mapVenueToResponseDTO).toList();
    }

    /**
     * fetch a venue by Id
     * @param id
     * @return
     */
    public VenueResponseDTO getVenue(Long id) {
        Venue venue = venueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venue not found"));
        return mapVenueToResponseDTO(venue);
    }

    /**
     * soft deletes a venue
     * @param id
     */
    public void removeVenue (Long id) {
        Venue venue = venueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("venue not found"));
        venue.setActive(false);
        venueRepository.save(venue);
    }

    public Venue getVenueById (Long id) {
        return venueRepository.getById(id);
    }
}
