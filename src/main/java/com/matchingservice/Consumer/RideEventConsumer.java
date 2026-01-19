package com.matchingservice.Consumer;
import com.RideSharing.Common.Event.RideEventType;

import com.RideSharing.Common.Event.RideEvent;
import com.matchingservice.Service.MatchingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
@Component
@RequiredArgsConstructor
@Slf4j
public class RideEventConsumer {

    private final MatchingService matchingService;

    @KafkaListener(topics = "ride-events")
    public void consume(RideEvent event) {


        log.info("RideEvent received: {}", event);

        switch (event.getType()) {

            case RIDE_REQUESTED ->
                    matchingService.matchNewRide(event.getRideId());

            case DRIVER_CANCELLED ->
                    matchingService.reassignRide(event.getRideId());

            default -> {

            }
        }
    }
}
