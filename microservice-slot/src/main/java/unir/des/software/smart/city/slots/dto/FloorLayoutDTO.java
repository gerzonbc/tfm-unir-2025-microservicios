package unir.des.software.smart.city.slots.dto;

import java.util.List;

public record FloorLayoutDTO(
        String floorId,
        List<CellDTO> cells
) {
}
