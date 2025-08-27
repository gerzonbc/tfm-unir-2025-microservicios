package unir.des.software.smart.city.parking.services;

import com.mongodb.DuplicateKeyException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unir.des.software.smart.city.parking.dto.ParkingTypePSTRequest;
import unir.des.software.smart.city.parking.dto.ParkingTypePTCRequest;
import unir.des.software.smart.city.parking.dto.ParkingTypeResponse;
import unir.des.software.smart.city.parking.exception.BusinessException;
import unir.des.software.smart.city.parking.mapper.ParkingTypeMapper;
import unir.des.software.smart.city.parking.model.ParkingType;
import unir.des.software.smart.city.parking.repository.ParkingRepository;
import unir.des.software.smart.city.parking.repository.ParkingTypeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParkingTypeService {

    private final ParkingTypeMapper mapper;
    private final ParkingTypeRepository repository;
    private final ParkingRepository parkingRepository;
    private final ParkingService parkingService;


    public List<ParkingTypeResponse> getParkingTypes() {
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }

    public ParkingTypeResponse getParkingType(String id) {
        ParkingType entity = repository.findById(id)
                .orElseThrow(() -> new BusinessException("Tipo de estacionamiento no encontrado con id: " + id));
        return mapper.toResponse(entity);
    }

    @Transactional
    public ParkingTypeResponse createParkingType(ParkingTypePSTRequest request) {
        if (repository.existsByNameIgnoreCase(request.getName())) {
            throw new BusinessException("El nombre del tipo de estacionamiento ya existe: " + request.getName());
        }
        ParkingType toSave = mapper.toEntity(request);
        ParkingType saved;
        try {
            saved = repository.save(toSave);
        } catch (DuplicateKeyException e) {
            throw new BusinessException("El nombre del tipo de estacionamiento ya existe: " + request.getName());
        }
        return mapper.toResponse(saved);
    }

    @Transactional
    public ParkingTypeResponse updateParkingType(String id, ParkingTypePTCRequest request) {
        ParkingType entity = repository.findById(id)
                .orElseThrow(() -> new BusinessException("Tipo de estacionamiento no encontrado con id: " + id));

        // Si cambia el nombre, valida duplicados
        if (request.getName() != null &&
                !request.getName().equalsIgnoreCase(entity.getName()) &&
                repository.existsByNameIgnoreCase(request.getName())) {
            throw new BusinessException("El nombre del tipo de estacionamiento ya existe: " + request.getName());
        }

        mapper.updateEntity(request, entity);

        ParkingType saved;
        try {
            saved = repository.save(entity);
        } catch (DuplicateKeyException e) {
            throw new BusinessException("El nombre del tipo de estacionamiento ya existe: " + request.getName());
        }
        parkingService.syncTypeByCode(saved.getId());
        return mapper.toResponse(saved);
    }

    public void deleteParkingType(String id) {
        if (!repository.existsById(id)) {
            throw new BusinessException("Tipo de estacionamiento no encontrado con id: " + id);
        }
        long refs = parkingRepository.countByTypeId(id);
        if (refs > 0) {
            throw new BusinessException("No se puede eliminar: hay " + refs + " parkings con este tipo.");
        }
        repository.deleteById(id);
    }
}
