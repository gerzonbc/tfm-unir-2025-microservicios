package unir.des.software.aparcamientos.establecimientos.controllers.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EstablecimientoDto {
    private String nombre;
    private String ubicacion;
    private String color;
}
