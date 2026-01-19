package com.matchingservice.Service;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

    @Service
    public class GeocodingService {

        private final RestTemplate restTemplate;

        public GeocodingService(RestTemplateBuilder builder) {
            this.restTemplate = builder.build();
        }

        // Convert address → coordinates
        public Optional<double[]> getCoordinates(String address) {
            String url = "https://nominatim.openstreetmap.org/search?q={address}&format=json&limit=1";
            ResponseEntity<List> response = restTemplate.getForEntity(url, List.class, address);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null && !response.getBody().isEmpty()) {
                Map firstResult = (Map) response.getBody().get(0);
                double lat = Double.parseDouble((String) firstResult.get("lat"));
                double lon = Double.parseDouble((String) firstResult.get("lon"));
                return Optional.of(new double[]{lat, lon});
            }
            return Optional.empty();
        }

        // Convert coordinates → address
        public Optional<String> getAddress(double lat, double lon) {
            String url = "https://nominatim.openstreetmap.org/reverse?lat={lat}&lon={lon}&format=json";
            Map response = restTemplate.getForObject(url, Map.class, lat, lon);
            return response != null ? Optional.of((String) response.get("display_name")) : Optional.empty();
        }
    }

}
