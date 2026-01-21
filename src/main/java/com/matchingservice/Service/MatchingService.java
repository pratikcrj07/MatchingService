package com.matchingservice.Service;

import com.RideSharing.Common.Dto.DriverOfferDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MatchingService {

    private final DriverSearchService driverSearchService;
    private final KafkaTemplate<String, DriverOfferDTO> kafkaTemplate;

    public void matchNewRide(Long rideId, double pickupLat, double pickupLon) {

        List<Long> drivers =
                driverSearchService.findNearbyDrivers(
                        pickupLat,
                        pickupLon,
                        5
                );

        if (drivers.isEmpty()) {
            log.warn("No drivers found for ride {}", rideId);
            return;
        }

        for (Long driverId : drivers) {
            DriverOfferDTO offer = new DriverOfferDTO(
                    rideId,
                    driverId,
                    pickupLat,
                    pickupLon,
                    250.0,
                    15
            );

            kafkaTemplate.send(
                    "driver-offers",
                    driverId.toString(),
                    offer
            );
        }
    }

    public void reassignRide(Long rideId, double pickupLat, double pickupLon) {
        matchNewRide(rideId, pickupLat, pickupLon);
    }
}
