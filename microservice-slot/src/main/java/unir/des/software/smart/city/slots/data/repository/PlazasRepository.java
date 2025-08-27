package unir.des.software.smart.city.slots.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import unir.des.software.smart.city.slots.data.model.Plaza;

public interface PlazasRepository extends MongoRepository<Plaza, String> {
}
