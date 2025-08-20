package unir.des.software.smart.city.slots.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import unir.des.software.smart.city.slots.data.model.Planta;

public interface PlantasRepository extends MongoRepository<Planta, String> {
}
