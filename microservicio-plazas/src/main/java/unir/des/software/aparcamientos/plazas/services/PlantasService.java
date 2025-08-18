
package unir.des.software.aparcamientos.plazas.services;

import unir.des.software.aparcamientos.plazas.controllers.model.PlantaDto;
import unir.des.software.aparcamientos.plazas.data.model.Planta;

import java.util.List;

public interface PlantasService {
    List<Planta> getPlantas();

    Planta getPlanta(String id);

    Boolean eliminarPlanta(String id);

    Planta crearPlanta(PlantaDto plantaCrear);

    Planta actualizarPlanta(String id, PlantaDto plantaModificar);
}
