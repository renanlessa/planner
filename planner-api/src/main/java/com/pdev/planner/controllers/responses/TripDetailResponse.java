package com.pdev.planner.controllers.responses;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
public class TripDetailResponse {
    private UUID id;
    private String destination;
    private LocalDateTime startsAt;
    private LocalDateTime endsAt;
    private boolean isConfirmed;
    private String ownerName;
    private String ownerEmail;
}
