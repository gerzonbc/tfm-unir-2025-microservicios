package unir.des.software.smart.city.slots.mapper;

import org.springframework.stereotype.Component;
import unir.des.software.smart.city.slots.dto.CellDTO;
import unir.des.software.smart.city.slots.dto.FloorLayoutDTO;
import unir.des.software.smart.city.slots.enums.CellType;
import unir.des.software.smart.city.slots.enums.SlotType;
import unir.des.software.smart.city.slots.model.Floor;
import unir.des.software.smart.city.slots.model.FloorLayout;
import unir.des.software.smart.city.slots.model.Slot;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Component
public class FloorLayoutMapper {


    public boolean isParkingCell(CellType t) {
        return switch (t) {
            case PARKING_SLOT, DISABLED -> true;
            default -> false;
        };
    }

    public CellType mapCellType(String type) {
        return switch (type.toLowerCase(Locale.ROOT)) {
            case "parking-slot" -> CellType.PARKING_SLOT;
            case "disabled" -> CellType.DISABLED;
            case "drive-lane" -> CellType.DRIVE_LANE;
            case "entrance-down" -> CellType.ENTRANCE_DOWN;
            case "entrance-left" -> CellType.ENTRANCE_LEFT;
            case "entrance-right" -> CellType.ENTRANCE_RIGHT;
            case "exit-right" -> CellType.EXIT_RIGHT;
            case "exit-left" -> CellType.EXIT_LEFT;
            case "exit-up" -> CellType.EXIT_UP;
            case "empty-space", "" -> CellType.EMPTY_SPACE;
            default -> throw new IllegalArgumentException("Unknown cell type: " + type);
        };
    }

    public SlotType mapSlotType(CellType t) {
        return switch (t) {
            case DISABLED -> SlotType.DISABLED;
            default -> SlotType.NORMAL;
        };
    }

    public FloorLayoutDTO toDTO(Floor floor, FloorLayout layout) {
        // index slots by id for occupancy status
        Map<String, Slot> byId = new HashMap<>();
        for (Slot s : floor.getSlots()) byId.put(s.getId(), s);

        List<CellDTO> cells = layout.getCells().stream().map(c -> {
            Slot s = c.getSlotId() != null ? byId.get(c.getSlotId()) : null;
            Boolean occupied = s != null ? s.isOccupied() : null;
            return new CellDTO(
                    c.getRow(), c.getCol(),
                    c.getType().name().toLowerCase(Locale.ROOT),
                    c.getSlotId(), c.getSlotCode(), occupied
            );
        }).toList();

        return new FloorLayoutDTO(floor.getId(), cells);
    }
}
