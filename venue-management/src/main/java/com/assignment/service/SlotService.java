package com.assignment.service;

import com.assignment.dao.SlotRepository;
import com.assignment.dto.SlotRequestDTO;
import com.assignment.entities.Slot;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class SlotService {
    private final SlotRepository slotRepository;
    private final VenueService venueService;
    private SportService sportService;
    public SlotService (SlotRepository slotRepository, VenueService venueService, SportService sportService) {
        this.slotRepository = slotRepository;
        this.venueService = venueService;
        this.sportService = sportService;
    }

    public void createSlot(SlotRequestDTO dto) {
        if(dto.getStartTime().isAfter(dto.getEndTime())) {
            throw new IllegalArgumentException("start time must be before end time");
        }

        boolean intervalOverlap =
                slotRepository.overlappingSlotExists(
                        dto.getVenueId(),
                        dto.getSportId(),
                        dto.getStartTime(),
                        dto.getEndTime()
                );

        if(intervalOverlap) {
            throw  new IllegalStateException("Slot overlaps with existing slot");
        }

        Slot slot = new Slot();
        slot.setVenue(venueService.getVenueById(dto.getVenueId()));
        slot.setSport(sportService.findSportById(dto.getSportId()));
        slot.setStartTime(dto.getStartTime());
        slot.setEndTime(dto.getEndTime());

        slotRepository.save(slot);
    }
}
