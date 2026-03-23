package me.proyecto.tienda.repository;

import me.proyecto.tienda.model.Producto;
import me.proyecto.tienda.model.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByCategoria(Categoria categoria);
    List<Producto> findByCategoria_Id(Long categoriaId);
    List<Producto> findByNombreContainingIgnoreCase(String nombre);
    Optional<Producto> findByNombre(String nombre);
    List<Producto> findByPrecioBetween(BigDecimal minPrice, BigDecimal maxPrice);
    List<Producto> findByCategoriaOrderByPrecioAsc(Categoria categoria);
    Page<Producto> findByCategoria(Categoria categoria, Pageable pageable);
    boolean existsByNombre(String nombre);
}

