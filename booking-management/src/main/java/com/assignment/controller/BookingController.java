package com.assignment.controller;

import com.assignment.dto.BookingRequestDTO;
import com.assignment.dto.BookingResponseDTO;
import com.assignment.service.BookingService;
import com.assignment.wrapper.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookings")
public class BookingController {
    private final BookingService bookingService;

    public BookingController (BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ApiResponse<BookingResponseDTO> book (@Valid @RequestBody BookingRequestDTO bookingRequestDTO) {
        return ApiResponse.success(
                bookingService.bookSlot(bookingRequestDTO.getSlotId()),
                "Slot Booked Successfully");
    }

    @PutMapping("/{id}/cancel")
    public ApiResponse<Void> cancel (@PathVariable Long id) {
        bookingService.cancelBooking(id);
        return ApiResponse.success(null, "Booking cancelled");
    }
}
