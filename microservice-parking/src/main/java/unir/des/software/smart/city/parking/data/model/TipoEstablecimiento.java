package unir.des.software.smart.city.parking.data.model;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import unir.des.software.smart.city.parking.controllers.model.TipoEstablecimientoDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Document(collection = "tipos_establecimientos")
public class TipoEstablecimiento {
    @Id
    private String id;
    private String nombre;
    private String color;

    public void prepareUpdate(TipoEstablecimientoDto tipoEstablecimientoDto){
        this.nombre = tipoEstablecimientoDto.getNombre();
        this.color = tipoEstablecimientoDto.getColor();
    }
}
