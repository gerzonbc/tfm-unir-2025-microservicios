package unir.des.software.smart.city.simulator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FloorDTO {
    String id;
    String parkingId;
    Integer number;
    List<SlotDTO> slots;
    FloorSummaryDTO summary;
}
