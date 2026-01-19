package com.matchingservice.Service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MatchingService {

    private final GeocodingService geocodingService;

    public void matchNewRide(Long rideId) {
        // Example: convert passenger address to coordinates
        Optional<double[]> coords = geocodingService.getCoordinates("Kathmandu, Nepal");
        coords.ifPresent(c -> {
            double lat = c[0];
            double lon = c[1];
            System.out.println("Passenger coordinates: " + lat + ", " + lon);

            // TODO: Use Redis geospatial to find nearby drivers
        });
    }

    public void reassignRide(Long rideId) {
    }
}
