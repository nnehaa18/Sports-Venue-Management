package com.assignment.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SlotRequestDTO {
    private Long venueId;
    private Long sportId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
