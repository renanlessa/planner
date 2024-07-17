package com.pdev.planner.services;

import com.pdev.planner.controllers.requests.LinkRequest;
import com.pdev.planner.entities.Link;
import com.pdev.planner.repositories.LinkRepository;
import com.pdev.planner.entities.Trip;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LinkService {

    private final LinkRepository repository;

    public String saveLink(LinkRequest request, Trip trip) {
        Link savedLink = repository.save(Link.builder()
                .title(request.title())
                .url(request.url())
                .trip(trip)
                .build());
        return savedLink.getTitle();
    }

    public List<Link> findLinksByTrip(UUID tripId) {
        return repository.findByTripId(tripId);
    }


}
