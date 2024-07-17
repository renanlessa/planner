package com.pdev.planner.repositories;

import com.pdev.planner.entities.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ActivityRepository extends JpaRepository<Activity, UUID> {

    List<Activity> findByTripId(UUID tripId);
}
