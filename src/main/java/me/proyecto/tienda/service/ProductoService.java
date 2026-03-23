package me.proyecto.tienda.service;

import me.proyecto.tienda.model.Producto;
import me.proyecto.tienda.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {
    
    @Autowired
    private ProductoRepository productoRepository;
    
    public List<Producto> obtenerTodos() {
        return productoRepository.findAll();
    }
    
    public Optional<Producto> obtenerPorId(Long id) {
        return productoRepository.findById(id);
    }
    
    public List<Producto> obtenerPorCategoria(Long categoriaId) {
        return productoRepository.findByCategoria_Id(categoriaId);
    }
    
    public List<Producto> buscarPorNombre(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre);
    }
    
    public Producto crear(Producto producto) {
        if (productoRepository.existsByNombre(producto.getNombre())) {
            throw new IllegalArgumentException("El producto con ese nombre ya existe");
        }
        
        producto.setFechaCreacion(LocalDateTime.now());
        producto.setFechaActualizacion(LocalDateTime.now());
        
        return productoRepository.save(producto);
    }
    
    public Producto actualizar(Long id, Producto productoActualizado) {
        Optional<Producto> producto = productoRepository.findById(id);
        
        if (producto.isEmpty()) {
            throw new IllegalArgumentException("Producto no encontrado");
        }
        
        Producto p = producto.get();
        
        if (productoActualizado.getNombre() != null && !productoActualizado.getNombre().equals(p.getNombre())) {
            if (productoRepository.existsByNombre(productoActualizado.getNombre())) {
                throw new IllegalArgumentException("El nombre del producto ya existe");
            }
            p.setNombre(productoActualizado.getNombre());
        }
        
        if (productoActualizado.getDescripcion() != null) {
            p.setDescripcion(productoActualizado.getDescripcion());
        }
        
        if (productoActualizado.getPrecio() != null) {
            p.setPrecio(productoActualizado.getPrecio());
        }
        
        if (productoActualizado.getCategoria() != null) {
            p.setCategoria(productoActualizado.getCategoria());
        }
        
        if (productoActualizado.getImagenUrl() != null) {
            p.setImagenUrl(productoActualizado.getImagenUrl());
        }
        
        p.setFechaActualizacion(LocalDateTime.now());
        
        return productoRepository.save(p);
    }
    
    public void eliminar(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new IllegalArgumentException("Producto no encontrado");
        }
        productoRepository.deleteById(id);
    }
}

