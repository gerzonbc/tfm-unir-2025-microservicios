package unir.des.software.smart.city.simulator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import unir.des.software.smart.city.simulator.enums.SlotType;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SlotDTO {
    String id;
    String code;
    SlotType type;
    boolean occupied;
    String sensorId;
    Instant lastSensorHeartbeat;
    String lastSensorValue;
}