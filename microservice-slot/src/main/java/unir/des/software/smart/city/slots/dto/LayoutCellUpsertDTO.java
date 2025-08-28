package unir.des.software.smart.city.slots.dto;

public record LayoutCellUpsertDTO(
        Integer id,      // ignorado en back si no lo quieres persistir
        int row,
        int col,
        String state,    // "available" | "" - opcional, FE
        String type      // "parking-slot", "disabled", "drive-lane", ...
) {}