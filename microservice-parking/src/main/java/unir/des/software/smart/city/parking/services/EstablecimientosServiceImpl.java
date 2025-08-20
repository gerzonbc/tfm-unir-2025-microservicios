package unir.des.software.smart.city.parking.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import unir.des.software.smart.city.parking.controllers.model.EstablecimientoDto;
import unir.des.software.smart.city.parking.data.model.Establecimiento;
import unir.des.software.smart.city.parking.data.repository.EstablecimientosRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class EstablecimientosServiceImpl implements EstablecimientosService {
    private final EstablecimientosRepository repository;

    @Override
    public List<Establecimiento> getEstablecimientos() {
        return repository.findAll();
    }

    @Override
    public Establecimiento getEstablecimiento(String id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Boolean eliminarEstablecimiento(String id) {
        Establecimiento establecimiento = repository.findById(id).orElse(null);
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
        Establecimiento establecimiento = repository.findById(id).orElse(null);
        if(establecimiento != null) {
            establecimiento.prepareUpdate(establecimientoModificar);
            repository.save(establecimiento);
            return establecimiento;
        }else{
            return null;
        }
    }
}
