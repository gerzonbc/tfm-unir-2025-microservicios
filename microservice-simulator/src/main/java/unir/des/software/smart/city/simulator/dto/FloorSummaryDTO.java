package unir.des.software.smart.city.simulator.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FloorSummaryDTO {
    int totalSlots;
    int occupiedSlots;
    int availableSlots;
}