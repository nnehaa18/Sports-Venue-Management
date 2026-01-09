package com.assignment.service;

import com.assignment.dao.BookingRepository;
import com.assignment.dao.SlotRepository;
import com.assignment.dto.BookingResponseDTO;
import com.assignment.entities.Booking;
import com.assignment.entities.Slot;
import com.assignment.enums.BookingStatus;
import com.assignment.exception.BookingNotFoundException;
import com.assignment.exception.SlotAlreadyBookedException;
import com.assignment.exception.SlotNotAvailableException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class BookingService {
    private final SlotRepository slotRepository;
    private final BookingRepository bookingRepository;

    public BookingService (SlotRepository slotRepository, BookingRepository bookingRepository) {
        this.slotRepository = slotRepository;
        this.bookingRepository = bookingRepository;
    }

    /**
     * to create a new booking
     * Approach: 2 conditions to avoid conflict: db lock and availability check
     * @param slotId
     * @return
     */
    @Transactional
    public BookingResponseDTO bookSlot (Long slotId) {
//        Step 1: lock slot for booking
        Slot slot = slotRepository.lockSlotForBooking(slotId)
                .orElseThrow(() -> new SlotNotAvailableException("Slot not available"));

//        Step 2: check if booking already exists against the slot
        bookingRepository.findBySlotIdAndStatus(slotId, BookingStatus.BOOKED)
                .ifPresent(b -> {
                    throw new SlotAlreadyBookedException("Slot already booked");
                });

//        Step 3: create booking
        Booking booking = new Booking();
        booking.setSlot(slot);
        booking.setStatus(BookingStatus.BOOKED);

        Booking bookingRecord = bookingRepository.save(booking);
        return new BookingResponseDTO(bookingRecord.getId(), slotId, "CONFIRMED");
    }

    /**
     * Cancel a booking
     * @param bookingId
     */
    @Transactional
    public void cancelBooking (Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found"));
        if(booking.getStatus() == BookingStatus.CANCELLED) {
            return;
        }
        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);
    }
}
