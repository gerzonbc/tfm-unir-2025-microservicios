package unir.des.software.smart.city.parking.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unir.des.software.smart.city.parking.dto.ParkingPSTRequest;
import unir.des.software.smart.city.parking.dto.ParkingPTCRequest;
import unir.des.software.smart.city.parking.dto.ParkingResponse;
import unir.des.software.smart.city.parking.exception.BusinessException;
import unir.des.software.smart.city.parking.mapper.ParkingMapper;
import unir.des.software.smart.city.parking.model.Parking;
import unir.des.software.smart.city.parking.model.ParkingType;
import unir.des.software.smart.city.parking.repository.ParkingRepository;
import unir.des.software.smart.city.parking.repository.ParkingTypeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParkingService {

    private final ParkingMapper mapper;
    private final ParkingRepository parkingRepository;
    private final ParkingTypeRepository typeRepository;
    private final MongoTemplate mongoTemplate;

    public List<ParkingResponse> getParkings() {
        return parkingRepository.findAll().stream().map(mapper::toResponse).toList();
    }


    public ParkingResponse getParking(String id) {
        Parking entity = parkingRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Parking not found with id: " + id));
        return mapper.toResponse(entity);
    }

    public List<ParkingResponse> findByTypeId(String code) {
        return parkingRepository.findByTypeId(code).stream().map(mapper::toResponse).toList();
    }

    /**
     * Búsqueda geoespacial opcional: devuelve parkings cerca de (lat,lng) dentro de un radio (en metros).
     * Requiere índice 2dsphere en "location" y que guardes location = [lng, lat].
     */
//    public List<ParkingResponse> getParkingsNear(double lat, double lng, double radiusMeters) {
//        Point center = new Point(lng, lat); // GeoJSON usa [lng, lat]
//        Distance max = new Distance(radiusMeters / 1000.0, Metrics.KILOMETERS);
//
//        Query q = new Query(Criteria.where("location").nearSphere(center).maxDistance(max.getNormalizedValue()));
//        return mongoTemplate.find(q, Parking.class).stream()
//                .map(mapper::toResponse)
//                .toList();
//    }

    public List<ParkingResponse> getParkingsNear(double lat, double lng, double radiusKiloMeters) {
        Point point = new Point(lng, lat); // GeoJSON usa [lng, lat]

        NearQuery near = NearQuery.near(point, Metrics.KILOMETERS)
                .maxDistance(new Distance(radiusKiloMeters, Metrics.KILOMETERS))
                .spherical(true);

        GeoResults<Parking> results = mongoTemplate.geoNear(near, Parking.class);

        return results.getContent().stream()
                .map(r -> mapper.toResponse(r.getContent()))
                .toList();
    }

    @Transactional
    public ParkingResponse createParking(ParkingPSTRequest request) {
        // 1) Resolver el tipo master por code
        ParkingType master = typeRepository.findById(request.getTypeId())
                .orElseThrow(() -> new BusinessException("typeId invalid: " + request.getTypeId()));

        // 2) Mapear request -> entity base
        Parking toSave = mapper.toEntity(request);

        // 3) Completar snapshot embebido y location
        toSave.setType(Parking.EmbeddedType.builder()
                .id(master.getId())
                .description(master.getDescription())
                .name(master.getName())
                .build());
        // GeoJSON = [lng, lat]
        toSave.setLocation(List.of(request.getLng(), request.getLat()));

        // 4) Guardar
        Parking saved = parkingRepository.save(toSave);
        return mapper.toResponse(saved);
    }

    @Transactional
    public ParkingResponse updateParking(String id, ParkingPTCRequest request) {
        Parking entity = parkingRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Parking not found with id: " + id));

        // Actualizar campos básicos desde el request (usa tu mapper para hacer patch/merge)
        mapper.updateEntity(request, entity);

        // Recalcular location si viene lat/lng en el request (o si mapper ya los copió)
        if (entity.getLat() != null && entity.getLng() != null) {
            entity.setLocation(List.of(entity.getLng(), entity.getLat()));
        }

        // Si cambia el typeCode (o viene no-nulo), regenerar snapshot desde el master
        if (request.getTypeId() != null && !request.getTypeId().isBlank()) {
            ParkingType master = typeRepository.findById(request.getTypeId())
                    .orElseThrow(() -> new BusinessException("typeId invalid: " + request.getTypeId()));

            entity.setType(Parking.EmbeddedType.builder()
                    .id(master.getId())
                            .description(master.getDescription())
                    .name(master.getName())
                    .build());
        }

        Parking saved = parkingRepository.save(entity);
        return mapper.toResponse(saved);
    }

    public void deleteParking(String id) {
        if (!parkingRepository.existsById(id)) {
            throw new BusinessException("Parking not found with id: " + id);
        }
        parkingRepository.deleteById(id);
    }

    /**
     * Propaga (sincroniza) el snapshot embebido (id/code/name) en todos los Parking que tengan type.code == code.
     * Úsalo cuando cambies el name/code del ParkingType master y quieras consistencia en los snapshots.
     * Devuelve la cantidad de documentos actualizados.
     */
    @Transactional
    public long syncTypeByCode(String code) {
        ParkingType master = typeRepository.findById(code)
                .orElseThrow(() -> new BusinessException("ParkingType not found with id: " + code));

        // Update masivo con MongoTemplate
        Query q = new Query(Criteria.where("type.id").is(code));
        var update = new org.springframework.data.mongodb.core.query.Update()
                .set("type.name", master.getName())
                .set("type.description", master.getDescription());

        return mongoTemplate.updateMulti(q, update, Parking.class).getModifiedCount();
    }
}
