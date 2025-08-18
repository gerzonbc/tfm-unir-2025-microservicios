package unir.des.software.aparcamientos.establecimientos.services;

import unir.des.software.aparcamientos.establecimientos.controllers.model.TipoEstablecimientoDto;
import unir.des.software.aparcamientos.establecimientos.data.model.TipoEstablecimiento;

import java.util.List;

public interface TiposEstablecimientoService {
    List<TipoEstablecimiento> getTiposEstablecimiento();

    TipoEstablecimiento getTipoEstablecimiento(String id);

    Boolean eliminarTipoEstablecimiento(String id);

    TipoEstablecimiento crearTipoEstablecimiento(TipoEstablecimientoDto tipoEstablecimientoCrear);

    TipoEstablecimiento actualizarTipoEstablecimiento(String id, TipoEstablecimientoDto tipoEstablecimientoModificar);
}
