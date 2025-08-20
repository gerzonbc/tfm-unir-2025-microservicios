package unir.des.software.smart.city.parking.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import unir.des.software.smart.city.parking.dto.ParkingTypeRequest;
import unir.des.software.smart.city.parking.dto.ParkingTypeResponse;
import unir.des.software.smart.city.parking.services.ParkingTypeService;

import java.net.URI;
import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/parking-types")
public class ParkingTypeController {

    private final ParkingTypeService service;

    @GetMapping
    public List<ParkingTypeResponse> getAll() {
        return service.getParkingTypes();
    }

    @GetMapping("/{id}")
    public ParkingTypeResponse getById(@PathVariable String id) {
        return service.getParkingType(id);
    }

    @PostMapping
    public ResponseEntity<ParkingTypeResponse> create(@Valid @RequestBody ParkingTypeRequest request) {
        ParkingTypeResponse created = service.createParkingType(request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()        // /api/v1/parking-types
                .path("/{id}")               // añade /{id}
                .buildAndExpand(created.getId())
                .toUri();

        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ParkingTypeResponse update(@PathVariable String id,
                                      @Valid @RequestBody ParkingTypeRequest request) {
        return service.updateParkingType(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.deleteParkingType(id);
        return ResponseEntity.noContent().build();
    }
}
