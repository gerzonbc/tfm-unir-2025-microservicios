package unir.des.software.smart.city.parking.mapper;

import org.springframework.stereotype.Component;
import unir.des.software.smart.city.parking.dto.ParkingTypeRequest;
import unir.des.software.smart.city.parking.dto.ParkingTypeResponse;
import unir.des.software.smart.city.parking.model.ParkingType;

@Component
public class ParkingTypeMapper {
    public ParkingType toEntity(ParkingTypeRequest req) {
        return ParkingType.builder()
                .name(req.getName())
                .description(req.getDescription())
                .build();
    }

    public ParkingTypeResponse toResponse(ParkingType entity) {
        return ParkingTypeResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .build();
    }

    public void updateEntity(ParkingTypeRequest req, ParkingType entity) {
        if (req.getName() != null) {
            entity.setName(req.getName());
        }
        if (req.getDescription() != null) {
            entity.setDescription(req.getDescription());
        }
    }
}
