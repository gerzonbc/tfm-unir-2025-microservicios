package unir.des.software.smart.city.simulator.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unir.des.software.smart.city.simulator.services.SimulatorScheduler;

import java.util.Map;

@Tag(name = "Simulator", description = "Simulador de ocupación de aparcamientos")
@RestController
@RequestMapping("/api/v1/simulator")
@RequiredArgsConstructor
public class SimulatorController {

    private final SimulatorScheduler scheduler;

    @PostMapping("/tick/floor/{floorId}")
    public Map<String, Integer> manualTick(@PathVariable String floorId,
                                           @RequestParam(defaultValue = "1") int count) {
//        int freed = scheduler.freeNInFloor(floorId, count);
//        return Map.of("floorId", floorId, "freed", freed);
        return scheduler.toggleAllInFloor(floorId);
    }

    @PostMapping("/tick/parking/{parkingId}") // opcional
    public ResponseEntity<Void> manualTickParking(@PathVariable String parkingId) {
        // podrías reusar el job principal pero filtrando por parkingId
        return ResponseEntity.accepted().build();
    }
}
