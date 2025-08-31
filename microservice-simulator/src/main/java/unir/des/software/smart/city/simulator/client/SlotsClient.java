package unir.des.software.smart.city.simulator.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import unir.des.software.smart.city.simulator.dto.FloorDTO;
import unir.des.software.smart.city.simulator.dto.FloorLayoutDTO;
import unir.des.software.smart.city.simulator.dto.OccupancyEventDTO;

@Component
@Slf4j
@RequiredArgsConstructor
public class SlotsClient {
    private final WebClient http = WebClient.builder().build();

    @Value("${targets.slots-base-url}")
    private String base;

    public FloorDTO[] getFloorsByParking(String parkingId) {
        return http.get()
                .uri(base + "/floors/by-parking/{id}", parkingId)
                .retrieve()
                .bodyToMono(FloorDTO[].class)
                .onErrorResume(e -> Mono.just(new FloorDTO[0]))
                .block();
    }

    public FloorLayoutDTO getFloorLayout(String floorId) {
        return http.get()
                .uri(base + "/floors/{id}/layout", floorId)
                .retrieve()
                .bodyToMono(FloorLayoutDTO.class)
                .block();
    }

    public boolean sendOccupyEvent(String floorId, String slotCode) {
        return sendEvent(floorId, slotCode, true, "simulator", java.time.Instant.now());
    }

    public boolean sendFreeEvent(String floorId, String slotCode) {
        return sendEvent(floorId, slotCode, false, "simulator", java.time.Instant.now());
    }

    private boolean sendEvent(String floorId,
                              String slotCode,
                              boolean occupied,
                              String source,
                              java.time.Instant ts) {
        OccupancyEventDTO body = OccupancyEventDTO.builder()
                .occupied(occupied)
                .source(source)
                .timestamp(ts)
                .build();

        try {
            return Boolean.TRUE.equals(http.post()
                    .uri(base + "/floors/{floorId}/slots/{slotCode}/occupancy-event", floorId, slotCode)
                    .bodyValue(body)
                    .retrieve()
                    .toBodilessEntity()
                    .map(resp -> resp.getStatusCode().is2xxSuccessful())
                    .onErrorReturn(false)
                    .block());
        } catch (Exception e) {
            log.error("Failed to send occupancy event to floor {} slot {}: {}", floorId, slotCode, e.getMessage());
            return false;
        }
    }

}
