package me.proyecto.tienda.repository;

import me.proyecto.tienda.model.DetalleOrden;
import me.proyecto.tienda.model.Orden;
import me.proyecto.tienda.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface DetalleOrdenRepository extends JpaRepository<DetalleOrden, Long> {
    List<DetalleOrden> findByOrden(Orden orden);
    List<DetalleOrden> findByProducto(Producto producto);
    List<DetalleOrden> findByOrdenOrderByIdAsc(Orden orden);
    Integer countByOrden(Orden orden);

    @Query("SELECT SUM(d.subtotal) FROM DetalleOrden d WHERE d.orden = :orden")
    BigDecimal sumSubtotalByOrden(@Param("orden") Orden orden);

    List<DetalleOrden> findByProductoAndOrden(Producto producto, Orden orden);
}
