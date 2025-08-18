package unir.des.software.aparcamientos.plazas.data.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import unir.des.software.aparcamientos.plazas.controllers.model.PlantaDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Document(collection = "plantas")
public class Planta {
    @Id
    private String id;
    private Integer numero;
    private String color;

    public void prepareUpdate(PlantaDto plantaDto){
        this.numero = plantaDto.getNumero();
        this.color = plantaDto.getColor();
    }
}
