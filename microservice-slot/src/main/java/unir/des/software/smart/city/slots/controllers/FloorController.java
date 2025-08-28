package unir.des.software.smart.city.slots.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import unir.des.software.smart.city.slots.dto.*;
import unir.des.software.smart.city.slots.services.FloorService;

import java.util.List;

@Tag(name = "Floors", description = "Gestión de Plantas y Plazas de Aparcamiento")
@RestController
@RequestMapping("/api/v1/floors")
@RequiredArgsConstructor
public class FloorController {

    private final FloorService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FloorDTO create(@RequestBody @Valid FloorCreateDTO req) {
        return service.create(req);
    }

    @GetMapping("/{floorId}")
    public FloorDTO get(@PathVariable String floorId) {
        return service.get(floorId);
    }

    @GetMapping("/by-parking/{parkingId}")
    public List<FloorDTO> listByParking(@PathVariable String parkingId) {
        return service.listByParking(parkingId);
    }

    // Nuevo endpoint con totales agregados
    @GetMapping("/by-parking/{parkingId}/with-summary")
    public ParkingFloorsResponse listByParkingWithSummary(@PathVariable String parkingId) {
        return service.listByParkingWithTotals(parkingId);
    }

    @PostMapping("/{floorId}/slots")
    @ResponseStatus(HttpStatus.CREATED)
    public FloorDTO addSlot(@PathVariable String floorId,
                            @RequestBody @Valid SlotCreateDTO req) {
        return service.addSlot(floorId, req);
    }

    @PutMapping("/{floorId}/slots/{slotCode}/sensor")
    public FloorDTO bindSensor(@PathVariable String floorId,
                               @PathVariable String slotCode,
                               @RequestBody @Valid BindSensorRequest req) {
        return service.bindSensor(floorId, slotCode, req);
    }

    /**
     * endpoint pensado para eventos del simulador
     */
    @PostMapping("/{floorId}/slots/{slotCode}/occupancy-event")
    public FloorDTO occupancyEvent(@PathVariable String floorId,
                                   @PathVariable String slotCode,
                                   @RequestBody @Valid OccupancyEventDTO event) {
        return service.applyOccupancyEvent(floorId, slotCode, event);
    }
}
