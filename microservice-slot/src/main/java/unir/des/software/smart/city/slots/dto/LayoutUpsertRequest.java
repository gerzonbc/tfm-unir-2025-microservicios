package unir.des.software.smart.city.slots.dto;

import java.util.List;

public record LayoutUpsertRequest(
        List<LayoutCellUpsertDTO> cells
) { }
