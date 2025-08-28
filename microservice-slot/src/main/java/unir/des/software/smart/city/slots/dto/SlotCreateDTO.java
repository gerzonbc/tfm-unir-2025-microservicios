package unir.des.software.smart.city.slots.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import unir.des.software.smart.city.slots.enums.SlotType;

public record SlotCreateDTO(
        @NotBlank String code,
        @NotNull SlotType type
) {}