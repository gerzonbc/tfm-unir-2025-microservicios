package unir.des.software.smart.city.simulator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParkingResponse {
    private String id;
    private String name;
    private String address;
    private Double lat;
    private Double lng;

    private ParkingTypeResponse type;
}
