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
public class ParkingTypeRequest {
    @NotBlank(message = "Nombre is requerido")
    @NotNull(message = "Nombre no puede ser nulo")
    private String name;
    private String description;
}
