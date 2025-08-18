package unir.des.software.aparcamientos.establecimientos.data.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import unir.des.software.aparcamientos.establecimientos.data.model.Establecimiento;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class EstablecimientosRepositoryImpl {
    private final EstablecimientosRepository repository;

    public List<Establecimiento> getAll(){ return repository.findAll(); }

    public Establecimiento getById(String id){ return repository.findById(id).orElse(null); }

    public Establecimiento save(Establecimiento establecimiento){ return repository.save(establecimiento); }

    public void delete(Establecimiento establecimiento){ repository.delete(establecimiento); }
}
