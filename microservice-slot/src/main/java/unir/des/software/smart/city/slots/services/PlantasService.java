
package unir.des.software.smart.city.slots.services;


import unir.des.software.smart.city.slots.controllers.model.PlantaDto;
import unir.des.software.smart.city.slots.data.model.Planta;

import java.util.List;

public interface PlantasService {
    List<Planta> getPlantas();

    Planta getPlanta(String id);

    Boolean eliminarPlanta(String id);

    Planta crearPlanta(PlantaDto plantaCrear);

    Planta actualizarPlanta(String id, PlantaDto plantaModificar);
}
