package unir.des.software.smart.city.slots.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unir.des.software.smart.city.slots.dto.*;
import unir.des.software.smart.city.slots.exception.BusinessException;
import unir.des.software.smart.city.slots.mapper.FloorMapper;
import unir.des.software.smart.city.slots.model.Floor;
import unir.des.software.smart.city.slots.model.Slot;
import unir.des.software.smart.city.slots.repository.FloorRepository;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FloorService {
    private final FloorRepository floorRepository;
    private final FloorMapper floorMapper;

    @Transactional
    public FloorDTO create(FloorCreateDTO req) {
        // Optional: validate parking existence via Parking microservice (REST call) here.
        Floor floor = floorRepository.save(floorMapper.toEntity(req));
        return floorMapper.toDTO(floor);
    }

    public List<FloorDTO> listByParking(String parkingId) {
        return floorRepository.findByParkingId(parkingId).stream()
                .map(floorMapper::toDTO)
                .toList();
    }

    public ParkingFloorsResponse listByParkingWithTotals(String parkingId) {
        List<Floor> floors = floorRepository.findByParkingId(parkingId);

        List<FloorDTO> floorDTOs = floors.stream()
                .map(floorMapper::toDTO)
                .toList();

        int total = 0;
        int occupied = 0;
        for (FloorDTO dto : floorDTOs) {
            total += dto.summary().totalSlots();
            occupied += dto.summary().occupiedSlots();
        }
        var overall = new FloorSummaryDTO(total, occupied, total - occupied);
        return new ParkingFloorsResponse(overall, floorDTOs);
    }


    public FloorDTO get(String floorId) {
        Floor floor = floorRepository.findById(floorId)
                .orElseThrow(() -> new BusinessException("Floor not found"));
        return floorMapper.toDTO(floor);
    }

    @Transactional
    public FloorDTO addSlot(String floorId, SlotCreateDTO req) {
        Floor floor = floorRepository.findById(floorId)
                .orElseThrow(() -> new BusinessException("Floor not found"));

        boolean exists = floor.getSlots().stream()
                .anyMatch(s -> s.getCode().equalsIgnoreCase(req.code()));
        if (exists) {
            throw new IllegalArgumentException("Slot code already exists on this floor");
        }

        Slot slot = floorMapper.toEntity(req);
        floor.getSlots().add(slot);
        return floorMapper.toDTO(floorRepository.save(floor));
    }

    @Transactional
    public FloorDTO bindSensor(String floorId, String slotCode, BindSensorRequest req) {
        Floor floor = floorRepository.findById(floorId)
                .orElseThrow(() -> new BusinessException("Floor not found"));

        Slot slot = floor.getSlots().stream()
                .filter(sl -> sl.getCode().equalsIgnoreCase(slotCode))
                .findFirst()
                .orElseThrow(() -> new BusinessException("Slot not found on this floor"));

        slot.setSensorId(req.sensorId());
        slot.setLastSensorHeartbeat(Instant.now());
        return floorMapper.toDTO(floorRepository.save(floor));
    }

    /**
     * Called by the simulator microservice
     */
    @Transactional
    public FloorDTO applyOccupancyEvent(String floorId, String slotCode, OccupancyEventDTO event) {
        Floor floor = floorRepository.findById(floorId)
                .orElseThrow(() -> new BusinessException("Floor not found"));

        Slot slot = floor.getSlots().stream()
                .filter(sl -> sl.getCode().equalsIgnoreCase(slotCode))
                .findFirst()
                .orElseThrow(() -> new BusinessException("Slot not found on this floor"));

        slot.setOccupied(Boolean.TRUE.equals(event.occupied()));
        slot.setLastSensorHeartbeat(Instant.now());
        slot.setLastSensorValue(event.value());

        return floorMapper.toDTO(floorRepository.save(floor));
    }
}
