package com.assignment.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "venues")
public class Venue extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String venueName;
    private String location;

    @ManyToMany
    @JoinTable(
            name = "venue_sports",
            joinColumns = @JoinColumn(name = "venue_id"),
            inverseJoinColumns = @JoinColumn(name = "sport_id")
    )
    private Set<Sport> sports = new HashSet<>();
}
