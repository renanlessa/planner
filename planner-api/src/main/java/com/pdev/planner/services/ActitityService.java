package com.pdev.planner.services;

import com.pdev.planner.controllers.requests.ActivityRequest;
import com.pdev.planner.entities.Activity;
import com.pdev.planner.entities.Trip;
import com.pdev.planner.repositories.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActitityService {

    private final ActivityRepository repository;

    private final TripService tripService;

    public String saveActivity(ActivityRequest request, Trip trip) {
        Activity savedActivity = repository.save(Activity.builder()
                .title(request.title())
                .occursAt(LocalDateTime.parse(request.occursAt(), DateTimeFormatter.ISO_DATE_TIME))
                .trip(trip)
                .build());
        return savedActivity.getTitle();
    }

    public Optional<LinkedHashMap<LocalDate, List<Activity>>> findActivitiesByTrip(UUID tripId) {
        Optional<Trip> tripWithActivities = tripService.findTripByIdWithActivities(tripId);

        return tripWithActivities.map(trip -> {
            Map<LocalDate, List<Activity>> map = trip.getActivities().stream()
                    .collect(Collectors.groupingBy(activity -> activity.getOccursAt().toLocalDate()));
            return Optional.of(addDatesWithoutActivity(trip, map));
        }).orElse(Optional.empty());
    }

    private LinkedHashMap<LocalDate, List<Activity>> addDatesWithoutActivity(Trip trip, Map<LocalDate, List<Activity>> map) {
        LocalDate initialDate = trip.getStartsAt().toLocalDate();
        LocalDate finalDate = trip.getEndsAt().toLocalDate();

        long daysBetween = ChronoUnit.DAYS.between(initialDate, finalDate);

        for (int i = 0; i <= daysBetween; i++) {
            LocalDate date = initialDate.plusDays(i);
            if (!map.containsKey(date)) {
                map.put(date, new ArrayList<>());
            }
        }

        return map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }
}
