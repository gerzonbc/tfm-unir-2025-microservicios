package unir.des.software.smart.city.parking.services;


import unir.des.software.smart.city.parking.controllers.model.EstablecimientoDto;
import unir.des.software.smart.city.parking.data.model.Establecimiento;

import java.util.List;

public interface EstablecimientosService {
    List<Establecimiento> getEstablecimientos();

    Establecimiento getEstablecimiento(String id);

    Boolean eliminarEstablecimiento(String id);

    Establecimiento crearEstablecimiento(EstablecimientoDto establecimientoCrear);

    Establecimiento actualizarEstablecimiento(String id, EstablecimientoDto establecimientoModificar);
}
