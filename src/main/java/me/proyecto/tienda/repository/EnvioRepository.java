package me.proyecto.tienda.repository;

import me.proyecto.tienda.model.Envio;
import me.proyecto.tienda.model.Orden;
import me.proyecto.tienda.model.enums.EstadoEnvio;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EnvioRepository extends JpaRepository<Envio, Long> {
    Optional<Envio> findByOrden(Orden orden);
    List<Envio> findByEstado(EstadoEnvio estado);
    Optional<Envio> findByNumeroSeguimiento(String numeroSeguimiento);
    List<Envio> findByFechaEnvioBetween(LocalDateTime inicio, LocalDateTime fin);
}

