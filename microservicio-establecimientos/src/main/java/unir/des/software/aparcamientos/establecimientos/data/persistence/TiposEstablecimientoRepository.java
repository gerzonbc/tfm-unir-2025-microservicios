package unir.des.software.aparcamientos.establecimientos.data.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import unir.des.software.aparcamientos.establecimientos.data.model.TipoEstablecimiento;

public interface TiposEstablecimientoRepository extends MongoRepository<TipoEstablecimiento, String> {
}
