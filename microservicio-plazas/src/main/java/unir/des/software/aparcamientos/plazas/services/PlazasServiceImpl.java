package unir.des.software.aparcamientos.plazas.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import unir.des.software.aparcamientos.plazas.controllers.model.PlazaDto;
import unir.des.software.aparcamientos.plazas.data.model.Plaza;
import unir.des.software.aparcamientos.plazas.data.persistence.PlazasRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PlazasServiceImpl implements PlazasService {
    private final PlazasRepository repository;

    @Override
    public List<Plaza> getPlazas() {
        return repository.findAll();
    }

    @Override
    public Plaza getPlaza(String id) {
         return repository.findById(id).orElse(null);
    }

    @Override
    public Boolean eliminarPlaza(String id) {
        Plaza plaza = repository.findById(id).orElse(null);
        if(plaza != null) {
            repository.delete(plaza);
            return Boolean.TRUE;
        }else {
            return Boolean.FALSE;
        }
    }

    @Override
    public Plaza crearPlaza(PlazaDto plazaCrear) {
        if(plazaCrear != null && plazaCrear.getEstado() != null && StringUtils.hasLength(plazaCrear.getTipo().trim())) {
            Plaza plaza = Plaza.builder().estado(plazaCrear.getEstado()).tipo(plazaCrear.getTipo()).build();
            return repository.save(plaza);
        }else{
            return null;
        }
    }

    @Override
    public Plaza actualizarPlaza(String id, PlazaDto plazaModificar) {
        Plaza plaza = repository.findById(id).orElse(null);
        if(plaza != null) {
            plaza.prepareUpdate(plazaModificar);
            repository.save(plaza);
            return plaza;
        }else{
            return null;
        }
    }
}
