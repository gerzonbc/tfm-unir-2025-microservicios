package unir.des.software.aparcamientos.plazas.data.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import unir.des.software.aparcamientos.plazas.data.model.Plaza;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PlazasRepositoryImpl {
    private final PlazasRepository repository;

    public List<Plaza> getAll(){ return repository.findAll(); }

    public Plaza getById(String id){ return repository.findById(id).orElse(null); }

    public Plaza save(Plaza plaza){ return repository.save(plaza); }

    public void delete(Plaza plaza){ repository.delete(plaza); }
}
