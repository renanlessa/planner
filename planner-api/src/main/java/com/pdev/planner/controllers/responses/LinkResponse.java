package com.pdev.planner.controllers.responses;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class LinkResponse {
    private UUID id;
    private String title;
    private String url;
}
