package com.pdev.planner.controllers.responses;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
public class ActivityListResponse {
    private UUID id;
    private String title;
    private LocalDateTime dateTime;
}
