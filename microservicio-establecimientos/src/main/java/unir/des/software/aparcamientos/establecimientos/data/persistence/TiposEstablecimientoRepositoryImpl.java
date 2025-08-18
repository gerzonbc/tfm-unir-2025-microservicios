package unir.des.software.aparcamientos.establecimientos.data.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import unir.des.software.aparcamientos.establecimientos.data.model.TipoEstablecimiento;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TiposEstablecimientoRepositoryImpl {
    private final TiposEstablecimientoRepository repository;

    public List<TipoEstablecimiento> getAll(){ return repository.findAll(); }

    public TipoEstablecimiento getById(String id){ return repository.findById(id).orElse(null); }

    public TipoEstablecimiento save(TipoEstablecimiento tipoEstablecimiento){ return repository.save(tipoEstablecimiento); }

    public void delete(TipoEstablecimiento tipoEstablecimiento){ repository.delete(tipoEstablecimiento); }
}
