package unir.des.software.aparcamientos.establecimientos.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import unir.des.software.aparcamientos.establecimientos.controllers.model.EstablecimientoDto;
import unir.des.software.aparcamientos.establecimientos.data.model.Establecimiento;
import unir.des.software.aparcamientos.establecimientos.data.persistence.EstablecimientosRepositoryImpl;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class EstablecimientosServiceImpl implements EstablecimientosService {
    private final EstablecimientosRepositoryImpl repository;

    @Override
    public List<Establecimiento> getEstablecimientos() {
        return repository.getAll();
    }

    @Override
    public Establecimiento getEstablecimiento(String id) {
        return repository.getById(id);
    }

    @Override
    public Boolean eliminarEstablecimiento(String id) {
        Establecimiento establecimiento = repository.getById(id);
        if(establecimiento != null) {
            repository.delete(establecimiento);
            return Boolean.TRUE;
        }else {
            return Boolean.FALSE;
        }
    }

    @Override
    public Establecimiento crearEstablecimiento(EstablecimientoDto establecimientoCrear) {
        if(establecimientoCrear != null && StringUtils.hasLength(establecimientoCrear.getNombre().trim()) && StringUtils.hasLength(establecimientoCrear.getUbicacion().trim()) && StringUtils.hasLength(establecimientoCrear.getColor().trim())) {
            Establecimiento establecimiento = Establecimiento.builder().nombre(establecimientoCrear.getNombre()).ubicacion(establecimientoCrear.getUbicacion()).color(establecimientoCrear.getColor()).build();
            return repository.save(establecimiento);
        }else{
            return null;
        }
    }

    @Override
    public Establecimiento actualizarEstablecimiento(String id, EstablecimientoDto establecimientoModificar) {
        Establecimiento establecimiento = repository.getById(id);
        if(establecimiento != null) {
            establecimiento.prepareUpdate(establecimientoModificar);
            repository.save(establecimiento);
            return establecimiento;
        }else{
            return null;
        }
    }
}
