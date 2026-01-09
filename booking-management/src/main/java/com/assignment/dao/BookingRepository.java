package com.assignment.dao;

import com.assignment.entities.Booking;
import com.assignment.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    Optional<Booking> findBySlotIdAndStatus (Long slotId, BookingStatus status);
}
