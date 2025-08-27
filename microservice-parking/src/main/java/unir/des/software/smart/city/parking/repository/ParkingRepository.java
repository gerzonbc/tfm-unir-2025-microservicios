package unir.des.software.smart.city.parking.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import unir.des.software.smart.city.parking.model.Parking;

import java.util.List;

public interface ParkingRepository extends MongoRepository<Parking, String> {
    List<Parking> findByTypeId(String typeId);
    long countByTypeId(String typeId);
}
