package com.assignment.controller;

import com.assignment.dto.SlotRequestDTO;
import com.assignment.dto.VenueRequestDTO;
import com.assignment.dto.VenueResponseDTO;
import com.assignment.service.AvailabilityService;
import com.assignment.service.SlotService;
import com.assignment.service.VenueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/venues")
public class VenueController {
    private final VenueService venueService;
    private final SlotService slotService;
    private final AvailabilityService availabilityService;

    public VenueController (VenueService venueService, SlotService slotService, AvailabilityService availabilityService) {
        this.venueService = venueService;
        this.slotService = slotService;
        this.availabilityService = availabilityService;
    }

    /**
     * add a venue
     * @param venueRequestDTO
     * @return
     */
    @PostMapping
    public ResponseEntity<VenueResponseDTO> addVenue (@RequestBody VenueRequestDTO venueRequestDTO) {
        return ResponseEntity.ok(venueService.addVenue(venueRequestDTO));
    }

    /**
     * lists all active venues
     * @return
     */
    @GetMapping
    public ResponseEntity<List<VenueResponseDTO>> listVenues () {
        return ResponseEntity.ok(venueService.listVenues());
    }

    /**
     * fetches a venue by id
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<VenueResponseDTO> getVenue (@PathVariable Long id) {
        return ResponseEntity.ok(venueService.getVenue(id));
    }

    /**
     * soft deletion of a venue
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVenue (@PathVariable Long id) {
        venueService.removeVenue(id);
        return ResponseEntity.ok("Venue deleted successfully");
    }

    /**
     * add a slot against a venue for a sport
     * @param venueId
     * @param dto
     * @return
     */
    @PostMapping("/{venueId}/slots")
    public ResponseEntity<Void> createSlot (@PathVariable Long venueId, @RequestBody SlotRequestDTO dto) {
        dto.setVenueId(venueId);
        slotService.createSlot(dto);
        return ResponseEntity.ok().build();
    }

    /**
     * to fetch availabale venues based on sport and time range
     * @param sportId
     * @param start
     * @param end
     * @return
     */
    @GetMapping("/available")
    public List<VenueResponseDTO> fetchAvailableVenues (@RequestParam Long sportId,
                                                        @RequestParam LocalDateTime start, @RequestParam LocalDateTime end) {
        return availabilityService.findAvailableVenues(sportId, start, end);
    }
}
