package unir.des.software.smart.city.parking.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import unir.des.software.smart.city.parking.data.model.Establecimiento;

public interface EstablecimientosRepository extends MongoRepository<Establecimiento, String> {
}
