package unir.des.software.smart.city.slots.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import unir.des.software.smart.city.slots.model.FloorLayout;

import java.util.Optional;

public interface FloorLayoutRepository extends MongoRepository<FloorLayout, String> {
    Optional<FloorLayout> findByFloorId(String floorId);
}
