package unir.des.software.smart.city.parking.data.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import unir.des.software.smart.city.parking.controllers.model.EstablecimientoDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Document(collection = "establecimientos")
public class Establecimiento {
    @Id
    private String id;
    private String nombre;
    private String ubicacion;
    private String color;

    @DBRef
    private TipoEstablecimiento tipo;

    public void prepareUpdate(EstablecimientoDto establecimientoDto){
        this.nombre = establecimientoDto.getNombre();
        this.ubicacion = establecimientoDto.getUbicacion();
        this.color = establecimientoDto.getColor();
    }
}
