package unir.des.software.aparcamientos.establecimientos.services;

import unir.des.software.aparcamientos.establecimientos.controllers.model.EstablecimientoDto;
import unir.des.software.aparcamientos.establecimientos.data.model.Establecimiento;

import java.util.List;

public interface EstablecimientosService {
    List<Establecimiento> getEstablecimientos();

    Establecimiento getEstablecimiento(String id);

    Boolean eliminarEstablecimiento(String id);

    Establecimiento crearEstablecimiento(EstablecimientoDto establecimientoCrear);

    Establecimiento actualizarEstablecimiento(String id, EstablecimientoDto establecimientoModificar);
}
