package com.pdev.planner.controllers.responses;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class ParticipantResponse {

    private UUID id;
    private String name;
    private String email;
    private boolean confirmed;
}
