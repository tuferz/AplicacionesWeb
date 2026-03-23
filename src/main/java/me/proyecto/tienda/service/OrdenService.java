package me.proyecto.tienda.service;

import me.proyecto.tienda.model.Orden;
import me.proyecto.tienda.model.DetalleOrden;
import me.proyecto.tienda.model.Usuario;
import me.proyecto.tienda.model.enums.EstadoOrden;
import me.proyecto.tienda.repository.OrdenRepository;
import me.proyecto.tienda.repository.DetalleOrdenRepository;
import me.proyecto.tienda.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrdenService {
    
    @Autowired
    private OrdenRepository ordenRepository;
    
    @Autowired
    private DetalleOrdenRepository detalleOrdenRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    public List<Orden> obtenerTodas() {
        return ordenRepository.findAll();
    }
    
    public Optional<Orden> obtenerPorId(Long id) {
        return ordenRepository.findById(id);
    }
    
    public List<Orden> obtenerPorUsuarioId(Long usuarioId) {
        return ordenRepository.findByUsuario_Id(usuarioId);
    }
    
    public Orden crear(Long usuarioId) {
        Optional<Usuario> usuario = usuarioRepository.findById(usuarioId);
        
        if (usuario.isEmpty()) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }
        
        Orden orden = Orden.builder()
                .usuario(usuario.get())
                .fechaCreacion(LocalDateTime.now())
                .fechaActualizacion(LocalDateTime.now())
                .estado(EstadoOrden.PENDIENTE)
                .total(BigDecimal.ZERO)
                .build();
        
        return ordenRepository.save(orden);
    }
    
    public DetalleOrden agregarDetalle(Long ordenId, DetalleOrden detalle) {
        Optional<Orden> orden = ordenRepository.findById(ordenId);
        
        if (orden.isEmpty()) {
            throw new IllegalArgumentException("Orden no encontrada");
        }
        
        if (detalle.getCantidad() <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
        }
        
        if (detalle.getPrecioUnitario() == null || detalle.getPrecioUnitario().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El precio unitario debe ser mayor a 0");
        }
        
        detalle.setOrden(orden.get());
        BigDecimal subtotal = detalle.getPrecioUnitario().multiply(BigDecimal.valueOf(detalle.getCantidad()));
        detalle.setSubtotal(subtotal);
        
        DetalleOrden detalleGuardado = detalleOrdenRepository.save(detalle);
        
        // Actualizar total de la orden
        actualizarTotalOrden(ordenId);
        
        return detalleGuardado;
    }
    
    public void eliminarDetalle(Long detalleId, Long ordenId) {
        Optional<DetalleOrden> detalle = detalleOrdenRepository.findById(detalleId);
        
        if (detalle.isEmpty()) {
            throw new IllegalArgumentException("Detalle de orden no encontrado");
        }
        
        detalleOrdenRepository.deleteById(detalleId);
        
        // Actualizar total de la orden
        actualizarTotalOrden(ordenId);
    }
    
    public Orden actualizar(Long id, Orden ordenActualizada) {
        Optional<Orden> orden = ordenRepository.findById(id);
        
        if (orden.isEmpty()) {
            throw new IllegalArgumentException("Orden no encontrada");
        }
        
        Orden o = orden.get();
        
        if (ordenActualizada.getEstado() != null) {
            o.setEstado(ordenActualizada.getEstado());
        }
        
        o.setFechaActualizacion(LocalDateTime.now());
        
        return ordenRepository.save(o);
    }
    
    public Orden cambiarEstado(Long id, EstadoOrden nuevoEstado) {
        Optional<Orden> orden = ordenRepository.findById(id);
        
        if (orden.isEmpty()) {
            throw new IllegalArgumentException("Orden no encontrada");
        }
        
        Orden o = orden.get();
        o.setEstado(nuevoEstado);
        o.setFechaActualizacion(LocalDateTime.now());
        
        return ordenRepository.save(o);
    }
    
    public void eliminar(Long id) {
        if (!ordenRepository.existsById(id)) {
            throw new IllegalArgumentException("Orden no encontrada");
        }
        ordenRepository.deleteById(id);
    }
    
    private void actualizarTotalOrden(Long ordenId) {
        Optional<Orden> orden = ordenRepository.findById(ordenId);
        
        if (orden.isPresent()) {
            Orden o = orden.get();
            BigDecimal total = o.getDetalles().stream()
                    .map(DetalleOrden::getSubtotal)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            
            o.setTotal(total);
            o.setFechaActualizacion(LocalDateTime.now());
            ordenRepository.save(o);
        }
    }
}

