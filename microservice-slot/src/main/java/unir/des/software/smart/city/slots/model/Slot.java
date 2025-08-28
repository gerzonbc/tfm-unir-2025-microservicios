package unir.des.software.smart.city.slots.model;

import lombok.*;
import unir.des.software.smart.city.slots.enums.SlotType;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Slot {
    private String id;              // UUID
    private String code;            // unique per floor (e.g., "A-01")
    private SlotType type;          // NORMAL/EV/...
    private boolean occupied;       // current state

    // Sensor binding (simulator micro will post events)
    private String sensorId;        // external sensor/device id
    private Instant lastSensorHeartbeat;
    private String lastSensorValue; // raw payload if useful
}
