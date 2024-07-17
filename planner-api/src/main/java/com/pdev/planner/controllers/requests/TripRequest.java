package com.pdev.planner.controllers.requests;

import java.util.List;

public record TripRequest(String destination,
                          String startsAt,
                          String endsAt,
                          String ownerEmail,
                          String ownerName,
                          List<String> emailsToInvite) {
}
