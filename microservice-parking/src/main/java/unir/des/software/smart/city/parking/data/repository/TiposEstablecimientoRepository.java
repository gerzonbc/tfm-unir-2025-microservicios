package unir.des.software.smart.city.parking.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import unir.des.software.smart.city.parking.data.model.TipoEstablecimiento;

public interface TiposEstablecimientoRepository extends MongoRepository<TipoEstablecimiento, String> {
}
