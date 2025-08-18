
package unir.des.software.aparcamientos.plazas.services;

import unir.des.software.aparcamientos.plazas.controllers.model.PlantaDto;
import unir.des.software.aparcamientos.plazas.controllers.model.PlazaDto;
import unir.des.software.aparcamientos.plazas.data.model.Plaza;

import java.util.List;

public interface PlazasService {
    List<Plaza> getPlazas();

    Plaza getPlaza(String id);

    Boolean eliminarPlaza(String id);

    Plaza crearPlaza(PlazaDto plazaCrear);

    Plaza actualizarPlaza(String id, PlazaDto plazaModificar);
}
