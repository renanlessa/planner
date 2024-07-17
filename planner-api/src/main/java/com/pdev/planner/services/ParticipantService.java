package com.pdev.planner.services;

import com.pdev.planner.entities.Trip;
import com.pdev.planner.entities.Participant;
import com.pdev.planner.repositories.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ParticipantService {

    private final ParticipantRepository repository;

    public void registerParticipants(List<String> participantsToInvite, Trip trip) {
        List<Participant> participants = participantsToInvite.stream()
                .map(email -> Participant.builder()
                        .email(email)
                        .trip(trip)
                        .isConfirmed(false)
                        .name("")
                        .build()).toList();

        repository.saveAll(participants);
    }

    public List<Participant> findParticipantsByTrip(UUID tripId) {
        return repository.findByTripId(tripId);
    }

    public void triggerConfirmationEmailToParticipants(UUID idTrip) {

    }

    public void triggerConfirmationEmailToParticipant(String email) {

    }


}
