package unir.des.software.smart.city.parking.mapper;

import org.springframework.stereotype.Component;
import unir.des.software.smart.city.parking.dto.ParkingPSTRequest;
import unir.des.software.smart.city.parking.dto.ParkingPTCRequest;
import unir.des.software.smart.city.parking.dto.ParkingResponse;
import unir.des.software.smart.city.parking.dto.ParkingTypeResponse;
import unir.des.software.smart.city.parking.model.Parking;

@Component
public class ParkingMapper {
    public Parking toEntity(ParkingPSTRequest req) {
        if (req == null) return null;

        return Parking.builder()
                .name(req.getName() != null ? req.getName().trim() : null)
                .address(req.getAddress() != null ? req.getAddress().trim() : null)
                .lat(req.getLat())
                .lng(req.getLng())
                .build();
    }

    public ParkingResponse toResponse(Parking entity) {
        if (entity == null) return null;

        String typeId = null;
        String typeDescription = null;
        String typeName = null;

        if (entity.getType() != null) {
            typeId = entity.getType().getId();
            typeDescription = entity.getType().getDescription();
            typeName = entity.getType().getName();
        }

        return ParkingResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .address(entity.getAddress())
                .lat(entity.getLat())
                .lng(entity.getLng())
                .type(new ParkingTypeResponse(
                        typeId,
                        typeName,
                        typeDescription
                ))
                .build();
    }

    public void updateEntity(ParkingPTCRequest req, Parking entity) {
        if (req == null || entity == null) return;

        if (req.getName() != null) {
            entity.setName(req.getName().trim());
        }
        if (req.getAddress() != null) {
            entity.setAddress(req.getAddress().trim());
        }
        if (req.getLat() != null) {
            entity.setLat(req.getLat());
        }
        if (req.getLng() != null) {
            entity.setLng(req.getLng());
        }
    }
}
