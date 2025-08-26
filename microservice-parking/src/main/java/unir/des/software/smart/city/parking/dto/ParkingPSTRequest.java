package unir.des.software.smart.city.parking.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParkingPSTRequest {

    @NotBlank
    private String name;
    @NotBlank
    private String address;
    @NotNull
    private Double lat;
    @NotNull
    private Double lng;

    @NotBlank
    private String typeId; // se envía el code del master
}
