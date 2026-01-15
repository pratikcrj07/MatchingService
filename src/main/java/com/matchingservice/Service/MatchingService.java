package com.matchingservice.Service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MatchingService {

    public void reassignRide(Long rideId) {
        // For now just log (this proves wiring works)
        log.info("Starting reassignment flow for rideId={}", rideId);


    }
}
