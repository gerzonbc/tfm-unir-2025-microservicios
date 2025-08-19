package unir.des.software.aparcamientos.plazas.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unir.des.software.aparcamientos.plazas.controllers.model.PlantaDto;
import unir.des.software.aparcamientos.plazas.data.model.Planta;
import unir.des.software.aparcamientos.plazas.services.PlantasService;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/aparcamientos")
public class PlantasController {
    private final PlantasService service;

    @GetMapping("/plantas")
    public ResponseEntity<List<Planta>> getPlantas(
            @RequestHeader Map<String, String> headers) {
        log.info("headers:{}", headers);
        List<Planta> plantas = service.getPlantas();
        if(plantas != null) {
            return ResponseEntity.ok(plantas);
        }else{
            return ResponseEntity.ok(Collections.emptyList());
        }
    }

    @GetMapping("/plantas/{plantaId}")
    public ResponseEntity<Planta> getPlanta(@PathVariable String plantaId) {
        log.info("Request recibido para planta:{}", plantaId);
        Planta planta = service.getPlanta(plantaId);
        if(planta != null) {
            return ResponseEntity.ok(planta);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/plantas/{plantaId}")
    public ResponseEntity<Void> deletePlanta(@PathVariable String plantaId) {
        Boolean eliminado = service.eliminarPlanta(plantaId);
        if(eliminado) {
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/plantas")
    public ResponseEntity<Planta> addPlanta(@RequestBody PlantaDto createBody) {
        Planta plantaCreada = service.crearPlanta(createBody);
        if(plantaCreada != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(plantaCreada);
        }else{
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/plantas/{plantaId}")
    public ResponseEntity<Planta> updatePlanta(@PathVariable String plantaId, @RequestBody PlantaDto plantaActualizar) {
        Planta plantaActualizada = service.actualizarPlanta(plantaId, plantaActualizar);
        if(plantaActualizada != null) {
            return ResponseEntity.ok(plantaActualizada);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
