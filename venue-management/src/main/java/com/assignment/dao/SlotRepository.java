package com.assignment.dao;

import com.assignment.entities.Slot;
import com.assignment.entities.Venue;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SlotRepository extends JpaRepository<Slot, Long> {
    @Query("""
            select count(s) > 0 from Slot s
            where s.venue.id = :venueId
            and s.sport.id = :sportId
            and s.active = true
            and s.startTime < :endTime
            and s.endTime > :startTime
            """)
    boolean overlappingSlotExists (
            Long venueId,
            Long sportId,
            LocalDateTime startTime,
            LocalDateTime endTime
    );

    @Query("""
            select distinct s.venue from Slot s
            where s.sport.id = :sportId
            and s.active = true
            and s.startTime < :endTimeRequest
            and s.endTime > :startTimeRequest
            """)
    List<Venue> findAvailableVenues(
            Long sportId,
            LocalDateTime startTimeRequest,
            LocalDateTime endTimeRequest
    );

    /**
     * To prevent booking conflicts, using row-level lock
     * @param slotId
     * @return
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select s  from Slot s where s.id = :slotId and s.active = true")
    Optional<Slot> lockSlotForBooking (@Param("slotId") Long slotId);

}
