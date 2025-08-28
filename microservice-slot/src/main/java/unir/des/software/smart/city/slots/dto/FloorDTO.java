package unir.des.software.smart.city.slots.dto;

import java.util.List;

public record FloorDTO(
        String id,
        String parkingId,
        Integer number,
        List<SlotDTO> slots,
        FloorSummaryDTO summary
) {
}
