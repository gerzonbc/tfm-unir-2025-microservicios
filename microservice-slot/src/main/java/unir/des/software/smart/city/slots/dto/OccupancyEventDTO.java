package unir.des.software.smart.city.slots.dto;

import jakarta.validation.constraints.NotNull;

public record OccupancyEventDTO(
        @NotNull Boolean occupied,
        String value // optional raw sensor value
) {}