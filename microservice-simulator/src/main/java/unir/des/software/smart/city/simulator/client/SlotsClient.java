package unir.des.software.smart.city.simulator.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import unir.des.software.smart.city.simulator.dto.FloorDTO;
import unir.des.software.smart.city.simulator.dto.FloorLayoutDTO;
import unir.des.software.smart.city.simulator.dto.OccupancyEventDTO;

@Component
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

    public void sendFreeEvent(String floorId, String slotCode) {
        OccupancyEventDTO body = OccupancyEventDTO.builder()
                .occupied(false)
                .source("simulator")
                .timestamp(java.time.Instant.now())
                .build();

        http.post()
                .uri(base + "/floors/{floorId}/slots/{slotCode}/occupancy-event", floorId, slotCode)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(Void.class)
                .onErrorResume(e -> Mono.empty())
                .block();
    }
}
