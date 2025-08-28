package unir.des.software.smart.city.slots.dto;

public record CellDTO(
        int row, int col, String type,
        String slotId, String slotCode, Boolean occupied
) {
}
