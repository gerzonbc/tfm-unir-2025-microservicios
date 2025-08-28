package unir.des.software.smart.city.slots.dto;

public record FloorSummaryView(
        String floorId,
        Integer levelNumber,
        FloorSummaryDTO summary
) {
}
