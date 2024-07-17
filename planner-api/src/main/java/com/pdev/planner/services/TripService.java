package com.pdev.planner.services;

import com.pdev.planner.entities.Trip;
import com.pdev.planner.repositories.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TripService {

    private final TripRepository tripRepository;

    public Trip saveTrip(Trip trip) {
        return tripRepository.save(trip);
    }

    public Trip confirmTrip(UUID tripId) {
        return null;
    }

    public Optional<Trip> findTripById(UUID tripId) {
        return tripRepository.findById(tripId);
    }

    public Optional<Trip> findTripByIdWithActivities(UUID tripId) {
        return tripRepository.findByIdWithActivities(tripId);
    }

}
