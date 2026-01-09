package com.assignment.service;

import com.assignment.dao.SlotRepository;
import com.assignment.dto.VenueResponseDTO;
import com.assignment.mapper.VenueMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class AvailabilityService {

    private final SlotRepository slotRepository;
    public AvailabilityService (SlotRepository slotRepository) {
        this.slotRepository = slotRepository;
    }

    /**
     * find venues based on requested time range and sportID
     * @param sportId
     * @param startTime
     * @param endTime
     * @return
     */
    public List<VenueResponseDTO> findAvailableVenues (
            Long sportId,
            LocalDateTime startTime,
            LocalDateTime endTime
    ){
        return slotRepository.findAvailableVenues(sportId, startTime, endTime)
                .stream()
                .map(VenueMapper:: mapResponse)
                .toList();
    }
}
