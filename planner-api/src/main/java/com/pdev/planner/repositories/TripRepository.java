package com.pdev.planner.repositories;

import com.pdev.planner.entities.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface TripRepository extends JpaRepository<Trip, UUID> {

    @Query("SELECT t FROM Trip t JOIN FETCH t.activities WHERE t.id = :tripId")
    Optional<Trip> findByIdWithActivities(UUID tripId);

}
