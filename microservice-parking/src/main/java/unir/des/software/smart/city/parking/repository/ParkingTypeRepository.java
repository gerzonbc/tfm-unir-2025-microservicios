package unir.des.software.smart.city.parking.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import unir.des.software.smart.city.parking.model.ParkingType;

import java.util.Optional;

@Repository
public interface ParkingTypeRepository extends MongoRepository<ParkingType, String> {

    boolean existsByNameIgnoreCase(String name);
    Optional<ParkingType> findById(String id);
}
