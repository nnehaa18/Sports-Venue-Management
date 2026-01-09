//package com.assignment.dao;
//
//import com.assignment.entities.VenueSport;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.logging.LogManager;
//
//@Repository
//public interface VenueSportRepository extends JpaRepository<VenueSport, Long> {
//
//    @Query("""
//            select vs.sportId
//            from VenueSport vs where vs.venue.id = :venueId
//            """)
//    List<Long> findSportsIdByVenueId(@Param("venueId") Long venueId);
//}
