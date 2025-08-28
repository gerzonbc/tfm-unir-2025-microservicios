package unir.des.software.smart.city.slots.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import unir.des.software.smart.city.slots.dto.FloorLayoutDTO;
import unir.des.software.smart.city.slots.dto.LayoutUpsertRequest;
import unir.des.software.smart.city.slots.services.FloorLayoutService;

@Tag(name = "Floor Layout", description = "Gestión del Layout de la Planta")
@RestController
@RequestMapping("/api/v1/floors/{floorId}/layout")
@RequiredArgsConstructor
public class FloorLayoutController {

    private final FloorLayoutService service;

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FloorLayoutDTO upsert(@PathVariable String floorId, @RequestBody @Valid LayoutUpsertRequest req) {
        return service.upsertLayout(floorId, req);
    }

    @GetMapping
    public FloorLayoutDTO get(@PathVariable String floorId) {
        return service.getLayout(floorId);
    }
}
