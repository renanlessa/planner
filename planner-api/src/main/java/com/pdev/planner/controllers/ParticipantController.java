package com.pdev.planner.controllers;

import com.pdev.planner.entities.Participant;
import com.pdev.planner.repositories.ParticipantRepository;
import com.pdev.planner.controllers.requests.ParticipantRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/participants")
@RequiredArgsConstructor
public class ParticipantController {

    private final ParticipantRepository repository;

    @PostMapping("/{id}/confirm")
    public ResponseEntity<Participant> addParticipant(@PathVariable UUID id, @RequestBody ParticipantRequest request) {
        return repository.findById(id)
                .map(participant -> {
                    participant.setConfirmed(true);
                    participant.setName(request.name());
                    return ResponseEntity.ok(repository.save(participant));
                }).orElse(ResponseEntity.notFound().build());
    }
}
