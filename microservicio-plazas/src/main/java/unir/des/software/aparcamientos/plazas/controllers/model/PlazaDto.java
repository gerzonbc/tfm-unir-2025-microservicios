package unir.des.software.aparcamientos.plazas.controllers.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlazaDto {
    private Boolean estado;
    private String tipo;
}
