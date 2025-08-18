package unir.des.software.aparcamientos.plazas.data.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import unir.des.software.aparcamientos.plazas.data.model.Planta;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PlantasRepositoryImpl {
    private final PlantasRepository repository;

    public List<Planta> getAll(){ return repository.findAll(); }

    public Planta getById(String id){ return repository.findById(id).orElse(null); }

    public Planta save(Planta planta){ return repository.save(planta); }

    public void delete(Planta planta){ repository.delete(planta); }
}
