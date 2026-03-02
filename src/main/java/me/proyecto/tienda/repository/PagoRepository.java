package me.proyecto.tienda.repository;

import me.proyecto.tienda.model.Pago;
import me.proyecto.tienda.model.Orden;
import me.proyecto.tienda.model.enums.EstadoPago;
import me.proyecto.tienda.model.enums.MetodoPago;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PagoRepository extends JpaRepository<Pago, Long> {
    Optional<Pago> findByOrden(Orden orden);
    List<Pago> findByEstado(EstadoPago estado);
    List<Pago> findByMetodo(MetodoPago metodo);
    List<Pago> findByFechaPagoBetween(LocalDateTime inicio, LocalDateTime fin);
}

