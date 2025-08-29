package unir.des.software.smart.city.simulator.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import unir.des.software.smart.city.simulator.client.ParkingClient;
import unir.des.software.smart.city.simulator.client.SlotsClient;
import unir.des.software.smart.city.simulator.dto.CellDTO;
import unir.des.software.smart.city.simulator.dto.FloorDTO;
import unir.des.software.smart.city.simulator.dto.FloorLayoutDTO;
import unir.des.software.smart.city.simulator.dto.ParkingResponse;

import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SimulatorScheduler {

    private final ParkingClient parkingClient;
    private final SlotsClient slotsClient;

    @Value("${simulator.per-floor-free-count:1}")
    private int perFloorFreeCount;

    // Habilita scheduling en tu @SpringBootApplication con @EnableScheduling
    @Scheduled(cron = "${simulator.cron:0 */5 * * * *}") // default: cada 5 minutos
    public void freeOnePerFloorJob() {
        try {
            ParkingResponse[] parkings = parkingClient.getAllParkings();
            for (ParkingResponse p : parkings) {
                FloorDTO[] floors = slotsClient.getFloorsByParking(p.getId());
                for (FloorDTO f : floors) {
                    freeNInFloor(f.getId(), perFloorFreeCount);
                }
            }
            log.info("Simulator tick completed successfully");
        } catch (Exception ex) {
            log.error("Simulator tick failed", ex);
        }
    }

    // Para probar manualmente desde un Controller, puedes exponer este metodo
    public int freeNInFloor(String floorId, int n) {
        FloorLayoutDTO layout = slotsClient.getFloorLayout(floorId);
        if (layout == null || layout.getCells() == null) return 0;

        // Encuentra celdas ocupadas de slots y ordénalas por algo estable
        List<CellDTO> occupied = layout.getCells().stream()
                .filter(c -> c.getSlotCode() != null && Boolean.TRUE.equals(c.getOccupied()))
                .sorted(Comparator.comparing(CellDTO::getSlotCode))
                .toList();

        int freed = 0;
        for (CellDTO cellDTO : occupied) {
            slotsClient.sendFreeEvent(layout.getFloorId(), cellDTO.getSlotCode());
            freed++;
            if (freed >= n) {
                break;
            }
        }
        log.info("Freed {} slots in floor {}", freed, floorId);
        return freed;
    }
}
