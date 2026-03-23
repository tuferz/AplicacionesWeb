package me.proyecto.tienda.controller;

import me.proyecto.tienda.model.Orden;
import me.proyecto.tienda.model.DetalleOrden;
import me.proyecto.tienda.model.enums.EstadoOrden;
import me.proyecto.tienda.service.OrdenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/ordenes")
@CrossOrigin(origins = "*", maxAge = 3600)
public class OrdenController {
    
    @Autowired
    private OrdenService ordenService;
    
    @GetMapping
    public ResponseEntity<List<Orden>> obtenerTodas() {
        try {
            List<Orden> ordenes = ordenService.obtenerTodas();
            return ResponseEntity.ok(ordenes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Orden> obtenerPorId(@PathVariable Long id) {
        try {
            Optional<Orden> orden = ordenService.obtenerPorId(id);
            return orden.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Orden>> obtenerPorUsuario(@PathVariable Long usuarioId) {
        try {
            List<Orden> ordenes = ordenService.obtenerPorUsuarioId(usuarioId);
            return ResponseEntity.ok(ordenes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PostMapping
    public ResponseEntity<?> crear(@RequestParam Long usuarioId) {
        try {
            Orden nuevaOrden = ordenService.crear(usuarioId);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaOrden);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensaje", "Error al crear orden"));
        }
    }
    
    @PostMapping("/{id}/detalles")
    public ResponseEntity<?> agregarDetalle(@PathVariable Long id, @Valid @RequestBody DetalleOrden detalle) {
        try {
            DetalleOrden detalleGuardado = ordenService.agregarDetalle(id, detalle);
            return ResponseEntity.status(HttpStatus.CREATED).body(detalleGuardado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensaje", "Error al agregar detalle a la orden"));
        }
    }
    
    @DeleteMapping("/{id}/detalles/{detalleId}")
    public ResponseEntity<?> eliminarDetalle(@PathVariable Long id, @PathVariable Long detalleId) {
        try {
            ordenService.eliminarDetalle(detalleId, id);
            return ResponseEntity.ok(Map.of("mensaje", "Detalle eliminado correctamente"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensaje", "Error al eliminar detalle"));
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody Orden orden) {
        try {
            Orden ordenActualizada = ordenService.actualizar(id, orden);
            return ResponseEntity.ok(ordenActualizada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensaje", "Error al actualizar orden"));
        }
    }
    
    @PutMapping("/{id}/estado")
    public ResponseEntity<?> cambiarEstado(@PathVariable Long id, @RequestParam EstadoOrden estado) {
        try {
            Orden ordenActualizada = ordenService.cambiarEstado(id, estado);
            return ResponseEntity.ok(ordenActualizada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensaje", "Error al cambiar estado de la orden"));
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            ordenService.eliminar(id);
            return ResponseEntity.ok(Map.of("mensaje", "Orden eliminada correctamente"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensaje", "Error al eliminar orden"));
        }
    }
}

