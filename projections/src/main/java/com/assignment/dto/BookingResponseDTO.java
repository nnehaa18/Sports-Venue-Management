package com.assignment.dto;

public record BookingResponseDTO(
        Long bookingId,
        Long slotId,
        String status
) {}
