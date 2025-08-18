package unir.des.software.aparcamientos.establecimientos.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import unir.des.software.aparcamientos.establecimientos.controllers.model.TipoEstablecimientoDto;
import unir.des.software.aparcamientos.establecimientos.data.model.TipoEstablecimiento;
import unir.des.software.aparcamientos.establecimientos.data.persistence.TiposEstablecimientoRepositoryImpl;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TiposEstablecimientoServiceImpl implements TiposEstablecimientoService {
    private final TiposEstablecimientoRepositoryImpl repository;

    @Override
    public List<TipoEstablecimiento> getTiposEstablecimiento() {
        return repository.getAll();
    }

    @Override
    public TipoEstablecimiento getTipoEstablecimiento(String id) {
        return repository.getById(id);
    }

    @Override
    public Boolean eliminarTipoEstablecimiento(String id) {
        TipoEstablecimiento tipoEstablecimiento = repository.getById(id);
        if(tipoEstablecimiento != null) {
            repository.delete(tipoEstablecimiento);
            return Boolean.TRUE;
        }else {
            return Boolean.FALSE;
        }
    }

    @Override
    public TipoEstablecimiento crearTipoEstablecimiento(TipoEstablecimientoDto tipoEstablecimientoCrear) {
        if(tipoEstablecimientoCrear != null && StringUtils.hasLength(tipoEstablecimientoCrear.getNombre().trim())) {
            TipoEstablecimiento tipoEstablecimiento = TipoEstablecimiento.builder().nombre(tipoEstablecimientoCrear.getNombre()).color(tipoEstablecimientoCrear.getColor()).build();
            return repository.save(tipoEstablecimiento);
        }else{
            return null;
        }
    }

    @Override
    public TipoEstablecimiento actualizarTipoEstablecimiento(String id, TipoEstablecimientoDto tipoEstablecimientoModificar) {
        TipoEstablecimiento tipoEstablecimiento = repository.getById(id);
        if(tipoEstablecimiento != null) {
            tipoEstablecimiento.prepareUpdate(tipoEstablecimientoModificar);
            repository.save(tipoEstablecimiento);
            return tipoEstablecimiento;
        }else{
            return null;
        }
    }
}
