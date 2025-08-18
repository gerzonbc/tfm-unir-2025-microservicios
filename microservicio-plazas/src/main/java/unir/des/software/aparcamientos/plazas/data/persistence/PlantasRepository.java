package unir.des.software.aparcamientos.plazas.data.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import unir.des.software.aparcamientos.plazas.data.model.Planta;

public interface PlantasRepository extends MongoRepository<Planta, String> {
}
