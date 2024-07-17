package com.pdev.planner.controllers.mappers;

import com.pdev.planner.controllers.responses.LinkResponse;
import com.pdev.planner.entities.Link;

public class LinkMapper {

    public static LinkResponse toResponse(final Link link) {
        return LinkResponse.builder()
                .id(link.getId())
                .title(link.getTitle())
                .url(link.getUrl())
                .build();
    }
}
