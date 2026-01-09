package com.assignment.entities;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class CompositeVenueSportId {
    private Long venueId;
    private Long sportId;

    public CompositeVenueSportId() {}

    public CompositeVenueSportId(Long venueId, Long sportId) {
        this.venueId = venueId;
        this.sportId = sportId;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(!(obj instanceof CompositeVenueSportId)) return false;
        CompositeVenueSportId that = (CompositeVenueSportId) obj;
        return Objects.equals(venueId, that.venueId) && Objects.equals(sportId, that.sportId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(venueId, sportId);
    }
}
