package unir.des.software.smart.city.parking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParkingPTCRequest {

    private String name;
    private String address;
    private Double lat;
    private Double lng;
    private String typeId;
}
