
package unir.des.software.smart.city.slots.services;


import unir.des.software.smart.city.slots.controllers.model.PlazaDto;
import unir.des.software.smart.city.slots.data.model.Plaza;

import java.util.List;

public interface PlazasService {
    List<Plaza> getPlazas();

    Plaza getPlaza(String id);

    Boolean eliminarPlaza(String id);

    Plaza crearPlaza(PlazaDto plazaCrear);

    Plaza actualizarPlaza(String id, PlazaDto plazaModificar);
}
