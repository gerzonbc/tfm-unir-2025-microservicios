package unir.des.software.smart.city.slots.dto;

public record FloorSummaryDTO(
        int totalSlots,
        int occupiedSlots,
        int availableSlots
) {
}