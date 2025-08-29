package unir.des.software.smart.city.simulator.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import unir.des.software.smart.city.simulator.dto.ParkingResponse;

@Component
@RequiredArgsConstructor
public class ParkingClient {

    private final WebClient http = WebClient.builder().build();

    @Value("${targets.parking-base-url}")
    private String base;

    public ParkingResponse[] getAllParkings() {
        return http.get()
                .uri(base) // GET /api/v1/parkings
                .retrieve()
                .bodyToMono(ParkingResponse[].class)
                .onErrorResume(e -> Mono.just(new ParkingResponse[0]))
                .block();
    }
}
