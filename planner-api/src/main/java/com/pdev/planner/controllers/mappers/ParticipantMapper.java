package com.pdev.planner.controllers.mappers;

import com.pdev.planner.controllers.responses.ParticipantResponse;
import com.pdev.planner.entities.Participant;

public class ParticipantMapper {

    public static ParticipantResponse toResponse(final Participant participant) {
        return ParticipantResponse.builder()
                .id(participant.getId())
                .name(participant.getName())
                .email(participant.getEmail())
                .confirmed(participant.isConfirmed())
                .build();
    }
}
