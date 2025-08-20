package unir.des.software.smart.city.parking.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unir.des.software.smart.city.parking.controllers.model.TipoEstablecimientoDto;
import unir.des.software.smart.city.parking.data.model.TipoEstablecimiento;
import unir.des.software.smart.city.parking.services.TiposEstablecimientoService;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/aparcamientos")
public class TiposEstablecimientoController {
    private final TiposEstablecimientoService service;

    @GetMapping("/tipos-establecimientos")
    public ResponseEntity<List<TipoEstablecimiento>> getPersonas(
            @RequestHeader Map<String, String> headers) {
        log.info("headers:{}", headers);
        List<TipoEstablecimiento> tiposEstablecimiento = service.getTiposEstablecimiento();
        if(tiposEstablecimiento != null) {
            return ResponseEntity.ok(tiposEstablecimiento);
        }else{
            return ResponseEntity.ok(Collections.emptyList());
        }
    }

    @GetMapping("/tipos-establecimientos/{tipoEstablecimientoId}")
    public ResponseEntity<TipoEstablecimiento> getPersona(@PathVariable String tipoEstablecimientoId) {
        log.info("Request recibido para tipo de establecimiento:{}", tipoEstablecimientoId);
        TipoEstablecimiento tipoEstablecimiento = service.getTipoEstablecimiento(tipoEstablecimientoId);
        if(tipoEstablecimiento != null) {
            return ResponseEntity.ok(tipoEstablecimiento);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/tipos-establecimientos/{tipoEstablecimientoId}")
    public ResponseEntity<Void> deleteTipoEstablecimiento(@PathVariable String tipoEstablecimientoId) {
        Boolean eliminado = service.eliminarTipoEstablecimiento(tipoEstablecimientoId);
        if(eliminado) {
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/tipos-establecimientos")
    public ResponseEntity<TipoEstablecimiento> addTipoEstablecimiento(@RequestBody TipoEstablecimientoDto createBody) {
        TipoEstablecimiento tipoEstablecimientoCreado = service.crearTipoEstablecimiento(createBody);
        if(tipoEstablecimientoCreado != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(tipoEstablecimientoCreado);
        }else{
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/tipos-establecimientos/{tipoEstablecimientoId}")
    public ResponseEntity<TipoEstablecimiento> updateTipoEstablecimiento(@PathVariable String tipoEstablecimientoId, @RequestBody TipoEstablecimientoDto tipoEstablecimientoActualizar) {
        TipoEstablecimiento tipoEstablecimientoActualizado = service.actualizarTipoEstablecimiento(tipoEstablecimientoId, tipoEstablecimientoActualizar);
        if(tipoEstablecimientoActualizado != null) {
            return ResponseEntity.ok(tipoEstablecimientoActualizado);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
