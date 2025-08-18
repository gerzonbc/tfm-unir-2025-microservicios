package unir.des.software.aparcamientos.establecimientos.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unir.des.software.aparcamientos.establecimientos.controllers.model.EstablecimientoDto;
import unir.des.software.aparcamientos.establecimientos.controllers.model.TipoEstablecimientoDto;
import unir.des.software.aparcamientos.establecimientos.data.model.Establecimiento;
import unir.des.software.aparcamientos.establecimientos.data.model.TipoEstablecimiento;
import unir.des.software.aparcamientos.establecimientos.services.EstablecimientosService;
import unir.des.software.aparcamientos.establecimientos.services.TiposEstablecimientoService;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/aparcamientos")
public class EstablecimientosController {
    private final EstablecimientosService service;

    @GetMapping("/establecimientos/")
    public ResponseEntity<List<Establecimiento>> getEstablecimientos(
            @RequestHeader Map<String, String> headers) {
        log.info("headers:{}", headers);
        List<Establecimiento> establecimientos = service.getEstablecimientos();
        if(establecimientos != null) {
            return ResponseEntity.ok(establecimientos);
        }else{
            return ResponseEntity.ok(Collections.emptyList());
        }
    }

    @GetMapping("/establecimientos/{establecimientoId}")
    public ResponseEntity<Establecimiento> getEstablecimiento(@PathVariable String establecimientoId) {
        log.info("Request recibido para establecimiento:{}", establecimientoId);
        Establecimiento establecimiento = service.getEstablecimiento(establecimientoId);
        if(establecimiento != null) {
            return ResponseEntity.ok(establecimiento);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/establecimientos/{establecimientoId}")
    public ResponseEntity<Void> deleteEstablecimiento(@PathVariable String establecimientoId) {
        Boolean eliminado = service.eliminarEstablecimiento(establecimientoId);
        if(eliminado) {
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/establecimientos")
    public ResponseEntity<Establecimiento> addEstablecimiento(@RequestBody EstablecimientoDto createBody) {
        Establecimiento establecimientoCreado = service.crearEstablecimiento(createBody);
        if(establecimientoCreado != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(establecimientoCreado);
        }else{
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/establecimientos/{establecimientoId}")
    public ResponseEntity<Establecimiento> updateEstablecimiento(@PathVariable String establecimientoId, @RequestBody EstablecimientoDto establecimientoActualizar) {
        Establecimiento establecimientoActualizado = service.actualizarEstablecimiento(establecimientoId, establecimientoActualizar);
        if(establecimientoActualizado != null) {
            return ResponseEntity.ok(establecimientoActualizado);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
