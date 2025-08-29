package unir.des.software.smart.city.simulator.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class OccupancyEventDTO {
    private Boolean occupied;
    private String source;     // "simulator"
    private Instant timestamp; // now
}