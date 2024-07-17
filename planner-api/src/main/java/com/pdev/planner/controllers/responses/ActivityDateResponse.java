package com.pdev.planner.controllers.responses;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Builder
@Data
public class ActivityDateResponse {
    private String date;
    private List<ActivityListResponse> activities;
}
