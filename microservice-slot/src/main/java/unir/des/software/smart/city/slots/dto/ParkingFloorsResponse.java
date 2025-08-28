package unir.des.software.smart.city.slots.dto;

import java.util.List;

public record ParkingFloorsResponse(
        FloorSummaryDTO overall,   // totales sumados de todos los pisos
        List<FloorDTO> floors     // lista de pisos con su resumen
) {
}
