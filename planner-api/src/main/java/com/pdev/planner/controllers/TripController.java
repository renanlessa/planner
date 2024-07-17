package com.pdev.planner.controllers;

import com.pdev.planner.controllers.mappers.ActivityMapper;
import com.pdev.planner.controllers.mappers.LinkMapper;
import com.pdev.planner.controllers.mappers.ParticipantMapper;
import com.pdev.planner.controllers.mappers.TripMapper;
import com.pdev.planner.controllers.requests.ActivityRequest;
import com.pdev.planner.controllers.requests.LinkRequest;
import com.pdev.planner.controllers.requests.ParticipantRequest;
import com.pdev.planner.controllers.requests.TripRequest;
import com.pdev.planner.controllers.responses.ActivityDateResponse;
import com.pdev.planner.controllers.responses.LinkResponse;
import com.pdev.planner.controllers.responses.ParticipantResponse;
import com.pdev.planner.controllers.responses.TripDetailResponse;
import com.pdev.planner.entities.Activity;
import com.pdev.planner.entities.Trip;
import com.pdev.planner.services.ActitityService;
import com.pdev.planner.services.LinkService;
import com.pdev.planner.services.ParticipantService;
import com.pdev.planner.services.TripService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/trips")
@RequiredArgsConstructor
@Slf4j
public class TripController {

    private final TripService tripService;
    private final ParticipantService participantService;
    private final ActitityService actitityService;
    private final LinkService linkService;

    @PostMapping
    public ResponseEntity<TripDetailResponse> createTrip(@RequestBody TripRequest request) {
        log.info("Creating trip to: {} ", request.ownerName());

        Trip savedTrip = tripService.saveTrip(TripMapper.toTrip(request));
        this.participantService.registerParticipants(request.emailsToInvite(), savedTrip);

        return ResponseEntity.ok(TripMapper.toResponse(savedTrip));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TripDetailResponse> getTripDetails(@PathVariable UUID id) {
        log.info("Getting trip details from tripId: {} ", id);
        return tripService.findTripById(id)
                .map(trip -> ResponseEntity.ok(TripMapper.toResponse(trip)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Trip> updateTrip(@PathVariable UUID id, @RequestBody TripRequest request) {
        return tripService.findTripById(id)
                .map(trip -> {
                    trip.setDestination(request.destination());
                    trip.setStartsAt(LocalDateTime.parse(request.startsAt(), DateTimeFormatter.ISO_DATE_TIME));
                    trip.setEndsAt(LocalDateTime.parse(request.endsAt(), DateTimeFormatter.ISO_DATE_TIME));
                    return ResponseEntity.ok(tripService.saveTrip(trip));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/confirm")
    public ResponseEntity<Trip> confirmTrip(@PathVariable UUID id) {
        return tripService.findTripById(id)
                .map(trip -> {
                    trip.setConfirmed(true);

                    this.participantService.triggerConfirmationEmailToParticipants(trip.getId());

                    return ResponseEntity.ok(tripService.saveTrip(trip));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("{id}/invite")
    public ResponseEntity<Void> inviteParticipant(@PathVariable UUID id, @RequestBody ParticipantRequest request) {
        return tripService.findTripById(id)
                .map(trip -> {
                    this.participantService.registerParticipants(List.of(request.email()), trip);

                    if (trip.isConfirmed()) {
                        this.participantService.triggerConfirmationEmailToParticipant(request.email());
                    }
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/participants")
    public ResponseEntity<List<ParticipantResponse>> getParticipantsByTrip(@PathVariable UUID id) {
        log.info("Getting participants from tripId: {} ", id);
        List<ParticipantResponse> participants = participantService.findParticipantsByTrip(id)
                .stream().map(ParticipantMapper::toResponse).toList();

        if (participants.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(participants);
    }

    @PostMapping("{id}/activities")
    public ResponseEntity<Void> registerActivities(@PathVariable UUID id, @RequestBody ActivityRequest request) {
        return tripService.findTripById(id)
                .map(trip -> {
                    this.actitityService.saveActivity(request, trip);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/activities")
    public ResponseEntity<List<ActivityDateResponse>> getActivitiesByTrip(@PathVariable UUID id) {
        log.info("Getting activities from tripId: {} ", id);
        Optional<LinkedHashMap<LocalDate, List<Activity>>> activitiesByTrip = actitityService.findActivitiesByTrip(id);
        return activitiesByTrip.map(map -> ResponseEntity.ok(ActivityMapper.toListActivityDateResponse(map)))
                .orElse(ResponseEntity.ok(new ArrayList<>()));
    }

    @PostMapping("{id}/links")
    public ResponseEntity<Void> registerLinks(@PathVariable UUID id, @RequestBody LinkRequest request) {
        return tripService.findTripById(id)
                .map(trip -> {
                    this.linkService.saveLink(request, trip);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/links")
    public ResponseEntity<List<LinkResponse>> getLinksByTrip(@PathVariable UUID id) {
        log.info("Getting links from tripId: {} ", id);
        List<LinkResponse> links = linkService.findLinksByTrip(id)
                .stream()
                .map(LinkMapper::toResponse)
                .toList();

        if (links.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(links);
    }
}
