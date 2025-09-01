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

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
@RequiredArgsConstructor
public class SimulatorScheduler {

    private static final Random random = new Random();
    private final ParkingClient parkingClient;
    private final SlotsClient slotsClient;
    private final AtomicInteger runCounter = new AtomicInteger(0); //

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
//                    freeNInFloor(f.getId(), perFloorFreeCount);
                    toggleAllInFloor(f.getId());
                }
            }
            log.info("Simulator tick completed successfully");
        } catch (Exception ex) {
            log.error("Simulator tick failed", ex);
        }
    }

    public Map<String, Integer> toggleAllInFloor(String floorId) {
        Optional<FloorLayoutDTO> layoutOpt = slotsClient.getFloorLayout(floorId);
        FloorLayoutDTO layout = layoutOpt.orElse(null);
        if (layout == null || layout.getCells() == null) {
            return Map.of("toggled", 0, "freed", 0, "occupied", 0);
        }

        // Toma solo celdas que representan un slot real
        List<CellDTO> slots = layout.getCells().stream()
                .filter(c -> c.getSlotCode() != null)
                // patrón estable: por fila y columna
                .sorted(Comparator
                        .comparing(CellDTO::getRow, Comparator.nullsLast(Integer::compareTo))
                        .thenComparing(CellDTO::getCol, Comparator.nullsLast(Integer::compareTo)))
                .toList();

        int toggled = 0, freed = 0, occupied = 0;

        for (CellDTO cellDTO : slots) {
            final boolean hasExecuteEvent = shouldExecuteEvent();
            if (!hasExecuteEvent) {
                continue;
            }
            boolean ok;
            if (cellDTO.getOccupied()) {
                ok = slotsClient.sendFreeEvent(layout.getFloorId(), cellDTO.getSlotCode());
                if (ok) {
                    freed++;
                    toggled++;
                }
            } else {
                ok = slotsClient.sendOccupyEvent(layout.getFloorId(), cellDTO.getSlotCode());
                if (ok) {
                    occupied++;
                    toggled++;
                }
            }
        }

        log.info("Toggled {} slots in floor {} (freed={}, occupied={})", toggled, floorId, freed, occupied);

        return Map.of("toggled", toggled, "freed", freed, "occupied", occupied);
    }

    private boolean shouldExecuteEvent() {
        int n = random.nextInt(1000);
        return (n % 2 != 0);
    }
}
