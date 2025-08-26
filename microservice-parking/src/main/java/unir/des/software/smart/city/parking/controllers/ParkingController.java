package unir.des.software.smart.city.parking.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import unir.des.software.smart.city.parking.dto.ParkingPSTRequest;
import unir.des.software.smart.city.parking.dto.ParkingPTCRequest;
import unir.des.software.smart.city.parking.dto.ParkingResponse;
import unir.des.software.smart.city.parking.services.ParkingService;

import java.net.URI;
import java.util.List;

@Tag(name = "Parkings", description = "CRUD de Aparcamientos")
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/parkings")
public class ParkingController {

    private final ParkingService service;

    @GetMapping
    public List<ParkingResponse> getAll() {
        return service.getParkings();
    }

    @GetMapping("/{id}")
    public ParkingResponse getById(@PathVariable String id) {
        return service.getParking(id);
    }

    @PostMapping
    public ResponseEntity<ParkingResponse> create(@Valid @RequestBody ParkingPSTRequest request) {
        ParkingResponse created = service.createParking(request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();

        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ParkingResponse update(@PathVariable String id,
                                  @Valid @RequestBody ParkingPTCRequest request) {
        return service.updateParking(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.deleteParking(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search/by-type")
    public List<ParkingResponse> byType(@RequestParam String typeCode) {
        return service.findByTypeId(typeCode);
    }

    @GetMapping("/search/near")
    public List<ParkingResponse> near(@RequestParam double lat,
                                      @RequestParam double lng,
                                      @RequestParam double radiusKiloMeters) {
        return service.getParkingsNear(lat, lng, radiusKiloMeters);
    }

    // ---- Admin: sincronizar snapshot del tipo (cuando cambie en el master) ----
    @PostMapping("/admin/sync-type")
    public ResponseEntity<String> syncType(@RequestParam String code) {
        long updated = service.syncTypeByCode(code);
        return ResponseEntity.ok("Parkings actualizados: " + updated);
    }
}
