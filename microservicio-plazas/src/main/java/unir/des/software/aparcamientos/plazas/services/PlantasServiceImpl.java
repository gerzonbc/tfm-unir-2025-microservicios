package unir.des.software.aparcamientos.plazas.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import unir.des.software.aparcamientos.plazas.controllers.model.PlantaDto;
import unir.des.software.aparcamientos.plazas.data.model.Planta;
import unir.des.software.aparcamientos.plazas.data.persistence.PlantasRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PlantasServiceImpl implements PlantasService {
    private final PlantasRepository repository;

    @Override
    public List<Planta> getPlantas() {
        return repository.findAll();
    }

    @Override
    public Planta getPlanta(String id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Boolean eliminarPlanta(String id) {
        Planta planta = repository.findById(id).orElse(null);
        if(planta != null) {
            repository.delete(planta);
            return Boolean.TRUE;
        }else {
            return Boolean.FALSE;
        }
    }

    @Override
    public Planta crearPlanta(PlantaDto plantaCrear) {
        if(plantaCrear != null && plantaCrear.getNumero() != null && StringUtils.hasLength(plantaCrear.getColor().trim())) {
            Planta planta = Planta.builder().numero(plantaCrear.getNumero()).color(plantaCrear.getColor()).build();
            return repository.save(planta);
        }else{
            return null;
        }
    }

    @Override
    public Planta actualizarPlanta(String id, PlantaDto plantaModificar) {
        Planta planta = repository.findById(id).orElse(null);
        if(planta != null) {
            planta.prepareUpdate(plantaModificar);
            repository.save(planta);
            return planta;
        }else{
            return null;
        }
    }
}
