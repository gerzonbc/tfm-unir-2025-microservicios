package unir.des.software.aparcamientos.plazas.data.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import unir.des.software.aparcamientos.plazas.data.model.Plaza;

public interface PlazasRepository extends MongoRepository<Plaza, String> {
}
