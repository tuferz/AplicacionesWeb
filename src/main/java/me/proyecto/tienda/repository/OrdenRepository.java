package me.proyecto.tienda.repository;

import me.proyecto.tienda.model.Orden;
import me.proyecto.tienda.model.Usuario;
import me.proyecto.tienda.model.enums.EstadoOrden;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface OrdenRepository extends JpaRepository<Orden, Long> {
    List<Orden> findByUsuario(Usuario usuario);
    List<Orden> findByUsuario_Id(Long usuarioId);
    List<Orden> findByEstado(EstadoOrden estado);
    List<Orden> findByUsuarioAndEstado(Usuario usuario, EstadoOrden estado);
    List<Orden> findByFechaCreacionBetween(LocalDateTime inicio, LocalDateTime fin);
    Page<Orden> findByUsuario(Usuario usuario, Pageable pageable);
}

