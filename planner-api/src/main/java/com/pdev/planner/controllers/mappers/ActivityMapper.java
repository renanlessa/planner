package com.pdev.planner.controllers.mappers;

import com.pdev.planner.controllers.responses.ActivityDateResponse;
import com.pdev.planner.controllers.responses.ActivityListResponse;
import com.pdev.planner.entities.Activity;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ActivityMapper {

    public static List<ActivityDateResponse> toListActivityDateResponse(LinkedHashMap<LocalDate, List<Activity>> map) {
        return map.entrySet()
                .stream()
                .map(localDateListEntry ->
                        ActivityDateResponse.builder()
                                .date(localDateListEntry.getKey().toString())
                                .activities(toListActivityListResponse(localDateListEntry.getValue()))
                                .build())
                .collect(Collectors.toList());

    }

    public static List<ActivityListResponse> toListActivityListResponse(List<Activity> activities) {
        return activities.stream()
                .map(activity -> ActivityListResponse.builder()
                        .id(activity.getId())
                        .title(activity.getTitle())
                        .dateTime(activity.getOccursAt())
                        .build())
                .collect(Collectors.toList());
    }
}
