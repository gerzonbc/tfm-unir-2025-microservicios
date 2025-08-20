package unir.des.software.smart.city.parking.services;


import unir.des.software.smart.city.parking.controllers.model.TipoEstablecimientoDto;
import unir.des.software.smart.city.parking.data.model.TipoEstablecimiento;

import java.util.List;

public interface TiposEstablecimientoService {
    List<TipoEstablecimiento> getTiposEstablecimiento();

    TipoEstablecimiento getTipoEstablecimiento(String id);

    Boolean eliminarTipoEstablecimiento(String id);

    TipoEstablecimiento crearTipoEstablecimiento(TipoEstablecimientoDto tipoEstablecimientoCrear);

    TipoEstablecimiento actualizarTipoEstablecimiento(String id, TipoEstablecimientoDto tipoEstablecimientoModificar);
}
