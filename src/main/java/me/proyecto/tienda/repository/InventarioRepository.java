package me.proyecto.tienda.repository;

import me.proyecto.tienda.model.Inventario;
import me.proyecto.tienda.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface InventarioRepository extends JpaRepository<Inventario, Long> {
    Optional<Inventario> findByProducto(Producto producto);
    List<Inventario> findByCantidadDisponibleLessThan(Integer cantidad);
}

