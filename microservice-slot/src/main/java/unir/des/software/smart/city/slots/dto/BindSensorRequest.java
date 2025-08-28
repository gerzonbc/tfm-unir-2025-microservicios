package unir.des.software.smart.city.slots.dto;

import jakarta.validation.constraints.NotBlank;

public record BindSensorRequest(
        @NotBlank String sensorId
) {}