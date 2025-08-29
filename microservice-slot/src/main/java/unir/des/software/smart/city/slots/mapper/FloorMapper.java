package unir.des.software.smart.city.slots.mapper;

import org.springframework.stereotype.Component;
import unir.des.software.smart.city.slots.dto.*;
import unir.des.software.smart.city.slots.model.Floor;
import unir.des.software.smart.city.slots.model.Slot;

import java.util.List;
import java.util.UUID;

@Component
public class FloorMapper {

    public FloorSummaryDTO computeSummary(Floor floor) {
        int total = 0;
        int occupied = 0;
        if (floor.getSlots() != null) {
            for (Slot s : floor.getSlots()) {
                total++;
                if (s.isOccupied()) {
                    occupied++;
                }
            }
        }
        return new FloorSummaryDTO(total, occupied, total - occupied);
    }

    public Floor toEntity(FloorCreateDTO dto) {
        return Floor.builder()
                .parkingId(dto.parkingId())
                .number(dto.number())
                .build();
    }

    public Slot toEntity(SlotCreateDTO dto) {
        return Slot.builder()
                .id(UUID.randomUUID().toString())
                .code(dto.code())
                .type(dto.type())
                .occupied(false)
                .build();
    }

    public FloorDTO toDTO(Floor floor) {
        List<SlotDTO> slots = floor.getSlots().stream()
                .map(this::toDTO)
                .toList();
        return new FloorDTO(
                floor.getId(),
                floor.getParkingId(),
                floor.getNumber(),
                slots,
                computeSummary(floor)
        );
    }

    public SlotDTO toDTO(Slot s) {
        return new SlotDTO(
                s.getId(),
                s.getCode(),
                s.getType(),
                s.isOccupied(),
                s.getSensorId(),
                s.getLastSensorHeartbeat(),
                s.getLastSensorValue()
        );
    }
}
