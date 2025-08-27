package unir.des.software.smart.city.slots.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unir.des.software.smart.city.slots.controllers.model.PlazaDto;
import unir.des.software.smart.city.slots.data.model.Plaza;
import unir.des.software.smart.city.slots.services.PlazasService;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/aparcamientos")
public class PlazasController {
    private final PlazasService service;

    @GetMapping("/plazas/")
    public ResponseEntity<List<Plaza>> getPlazas(
            @RequestHeader Map<String, String> headers) {
        log.info("headers:{}", headers);
        List<Plaza> plazas = service.getPlazas();
        if(plazas != null) {
            return ResponseEntity.ok(plazas);
        }else{
            return ResponseEntity.ok(Collections.emptyList());
        }
    }

    @GetMapping("/plazas/{plantaId}")
    public ResponseEntity<Plaza> getPlaza(@PathVariable String plazaId) {
        log.info("Request recibido para plaza:{}", plazaId);
        Plaza plaza = service.getPlaza(plazaId);
        if(plaza != null) {
            return ResponseEntity.ok(plaza);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/plazas/{plazaId}")
    public ResponseEntity<Void> deletePlaza(@PathVariable String plazaId) {
        Boolean eliminado = service.eliminarPlaza(plazaId);
        if(eliminado) {
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/plazas")
    public ResponseEntity<Plaza> addPlaza(@RequestBody PlazaDto createBody) {
        Plaza plazaCreada = service.crearPlaza(createBody);
        if(plazaCreada != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(plazaCreada);
        }else{
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/plazas/{plazaId}")
    public ResponseEntity<Plaza> updatePlaza(@PathVariable String plazaId, @RequestBody PlazaDto plazaActualizar) {
        Plaza plazaActualizada = service.actualizarPlaza(plazaId, plazaActualizar);
        if(plazaActualizada != null) {
            return ResponseEntity.ok(plazaActualizada);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
