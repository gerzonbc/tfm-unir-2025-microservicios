package unir.des.software.smart.city.parking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParkingTypeResponse {
    private String id;
    private String name;
    private String description;
}
