package unir.des.software.smart.city.slots.data.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import unir.des.software.smart.city.slots.controllers.model.PlazaDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Document(collection = "plazas")
public class Plaza {
    @Id
    private String id;
    private Boolean estado;
    private String tipo;

    public void prepareUpdate(PlazaDto plazaDto){
        this.estado = plazaDto.getEstado();
        this.tipo = plazaDto.getTipo();
    }
}
