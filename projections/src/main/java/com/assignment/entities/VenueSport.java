package com.assignment.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "venue_sports")
public class VenueSport extends BaseEntity{
    @EmbeddedId
    private CompositeVenueSportId id;
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("venueId")
    @JoinColumn(name = "venue_id")
    private Venue venue;

    @ManyToOne(fetch =  FetchType.LAZY)
    @MapsId("sportId")
    @JoinColumn(name = "sport_id")
    private Sport sport;

}
