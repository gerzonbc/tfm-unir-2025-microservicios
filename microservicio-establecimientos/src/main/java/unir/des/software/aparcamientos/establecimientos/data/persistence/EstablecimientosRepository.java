package unir.des.software.aparcamientos.establecimientos.data.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import unir.des.software.aparcamientos.establecimientos.data.model.Establecimiento;

public interface EstablecimientosRepository extends MongoRepository<Establecimiento, String> {
}
