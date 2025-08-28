package unir.des.software.smart.city.slots.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import unir.des.software.smart.city.slots.model.Floor;

import java.util.List;
import java.util.Optional;

public interface FloorRepository extends MongoRepository<Floor, String> {
    List<Floor> findByParkingId(String parkingId);
    Optional<Floor> findByParkingIdAndNumber(String parkingId, Integer number);
}
