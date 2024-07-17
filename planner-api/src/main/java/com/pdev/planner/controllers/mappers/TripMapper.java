package com.pdev.planner.controllers.mappers;

import com.pdev.planner.controllers.requests.TripRequest;
import com.pdev.planner.controllers.responses.TripDetailResponse;
import com.pdev.planner.entities.Trip;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TripMapper {

    public static TripDetailResponse toResponse(final Trip trip) {
        return TripDetailResponse.builder()
                .id(trip.getId())
                .destination(trip.getDestination())
                .ownerName(trip.getOwnerName())
                .ownerEmail(trip.getOwnerEmail())
                .startsAt(trip.getStartsAt())
                .endsAt(trip.getEndsAt())
                .isConfirmed(trip.isConfirmed())
                .build();
    }

    public static Trip toTrip(final TripRequest request) {
        return Trip.builder()
                .destination(request.destination())
                .ownerName(request.ownerName())
                .ownerEmail(request.ownerEmail())
                .isConfirmed(false)
                .startsAt(LocalDateTime.parse(request.startsAt(), DateTimeFormatter.ISO_DATE_TIME))
                .endsAt(LocalDateTime.parse(request.endsAt(), DateTimeFormatter.ISO_DATE_TIME))
                .build();
    }


}
