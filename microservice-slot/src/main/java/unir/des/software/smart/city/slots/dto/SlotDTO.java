package unir.des.software.smart.city.slots.dto;

import unir.des.software.smart.city.slots.enums.SlotType;

import java.time.Instant;

public record SlotDTO(
        String id,
        String code,
        SlotType type,
        boolean occupied,
        String sensorId,
        Instant lastSensorHeartbeat,
        String lastSensorValue
) {}