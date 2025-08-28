package unir.des.software.smart.city.slots.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FloorCreateDTO(
        @NotBlank
        String parkingId,
        @NotNull
        Integer number
) {
}
